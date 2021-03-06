package uk.ac.rdg.resc.edal.json;

import java.io.IOException;
import java.util.Map;

import org.opengis.referencing.cs.CoordinateSystem;
import org.opengis.referencing.cs.RangeMeaning;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.data.Preference;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Resource;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import uk.ac.rdg.resc.edal.dataset.Dataset;
import uk.ac.rdg.resc.edal.domain.Domain;
import uk.ac.rdg.resc.edal.domain.GridDomain;
import uk.ac.rdg.resc.edal.exceptions.EdalException;
import uk.ac.rdg.resc.edal.feature.DiscreteFeature;
import uk.ac.rdg.resc.edal.feature.Feature;
import uk.ac.rdg.resc.edal.feature.GridFeature;
import uk.ac.rdg.resc.edal.feature.ProfileFeature;
import uk.ac.rdg.resc.edal.grid.AbstractTransformedGrid;
import uk.ac.rdg.resc.edal.grid.RectilinearGrid;
import uk.ac.rdg.resc.edal.grid.RectilinearGridImpl;
import uk.ac.rdg.resc.edal.grid.ReferenceableAxisImpl;
import uk.ac.rdg.resc.edal.grid.TimeAxis;
import uk.ac.rdg.resc.edal.grid.TimeAxisImpl;
import uk.ac.rdg.resc.edal.grid.VerticalAxis;
import uk.ac.rdg.resc.edal.metadata.Parameter;
import uk.ac.rdg.resc.edal.position.HorizontalPosition;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CoverageResource extends ServerResource {
	
	static class FeatureMetadata {
		public final DomainMetadata domainMeta;
		public final RangeMetadata rangeMeta;
		public final String featureId;
		public final String datasetId;
		public final String name;
		public final Class<?> type;
		public FeatureMetadata(String datasetId, Feature feature) {
			this.datasetId = datasetId;
			this.featureId = feature.getId();
			this.domainMeta = new DomainMetadata(feature);
			this.rangeMeta = new RangeMetadata(feature);
			this.name = feature.getName();
			this.type = feature.getClass();
		}
	}
		
	// FIXME send Vary: Prefer header
	// see https://github.com/restlet/restlet-framework-java/issues/1202
	static class Embed {
		boolean domain, range;
		/**
		 * Inspects the profile media type parameter:
		 * 
		 * Accept: application/prs.coverage+json; profile="http://coveragejson.org/profiles/standalone"
		 */
		static Embed from(Resource resource, Embed merge) {
			boolean standalone = false;
			for (Preference<MediaType> preference : resource.getClientInfo().getAcceptedMediaTypes()) {
				MediaType type = preference.getMetadata();
				String profile = type.getParameters().getFirstValue("profile");
				if (profile != null && profile.contains(Constants.CovJSONProfileStandalone)) {
					standalone = true;
				}		
			}	
			return new Embed(standalone || merge.domain, standalone || merge.range);
		}
		
		public Embed(boolean domain, boolean range) {
			this.domain = domain || range;
			this.range = range;
		}
	}
		
	public static Builder getCoverageAsCovJson(Supplier<Dataset> dataset, FeatureMetadata meta, String rootUri, 
			Embed details, SubsetConstraint subset, boolean skipParameters) throws EdalException, IOException {
		String coverageUrl = rootUri + "/datasets/" + meta.datasetId + "/coverages/" + meta.featureId;
		String queryString = Constraint.getQueryString(subset.getCanonicalQueryParams());
		
		Builder j = ImmutableMap.builder()
				.put("type", "Coverage")
				.put("id", coverageUrl + queryString);
				
		Supplier<UniformFeature> feature =
				Suppliers.memoize(() -> new UniformFeature((DiscreteFeature)dataset.get().readFeature(meta.featureId)));

		try {
			if (details.domain) {
				Map domainJson = CoverageDomainResource.getDomainJson(feature.get(), subset, coverageUrl);
				j.put("domain", domainJson);
			} else {
				j.put("domain", coverageUrl + "/domain" + queryString);
				j.put("domainType", meta.domainMeta.getType());
			}
			if (!skipParameters) {
				j.put("parameters", getParametersJson(meta, rootUri));
			}
			j.put("ranges", getRangesJson(meta, feature, details.range, subset, rootUri));
		
		} catch (UnsupportedDomainException e) {
			// FIXME return proper error doc
			j.put("domain", unsupportedDomain(e.domain, e.info));
		}

		return j;
	}
	
	@Get("html")
	public Representation html() throws IOException, EdalException {
		getResponse().redirectSeeOther(Constants.CoverageHTMLUrlPrefix + getReference());
		return null;
	}
		
	@Get("geojson")
	public Representation geojson() throws IOException, EdalException {
		String datasetId = Reference.decode(getAttribute("datasetId"));
		String coverageId = Reference.decode(getAttribute("coverageId"));
		SubsetConstraint subset = new SubsetConstraint(getQuery());
		
		String coverageUrl = getRootRef() + "/datasets/" + datasetId + "/coverages" + "/" + coverageId;
		String queryString = Constraint.getQueryString(subset.getCanonicalQueryParams());
		
		getResponse().redirectSeeOther(coverageUrl + "/outlines" + queryString);
		return null;
	}
	
	@Get("covjson|covjsons|covcbor|covmsgpack")
	public Representation covjson() throws IOException, EdalException {
		addLinkHeaders();
		
		String datasetId = Reference.decode(getAttribute("datasetId"));
		String coverageId = Reference.decode(getAttribute("coverageId"));
		Embed defaultEmbed = new Embed(true, false);
		Embed embed = Embed.from(this, defaultEmbed);
		SubsetConstraint subset = new SubsetConstraint(getQuery());
		
		String rootUri = getRootRef().toString();
		String collectionUrl = rootUri + "/datasets/" + datasetId + "/coverages";
		String coverageUrl = collectionUrl + "/" + coverageId;
		String queryString = Constraint.getQueryString(subset.getCanonicalQueryParams());
		
		DatasetMetadata meta = DatasetResource.getDatasetMetadata(datasetId);
		
		Builder ldContext = ImmutableMap.builder();
		
		Builder coverageJson = getCoverageAsCovJson(meta.getLazyDataset(), 
				meta.getFeatureMetadata(coverageId), getRootRef().toString(), embed, subset, false)
				.put("@context", ImmutableList.of(
						Constants.CoverageJSONContext,
						ldContext
							.put(Constants.RdfsPrefix, Constants.RdfsNamespace)
							.put(Constants.CovAPIPrefix, Constants.CovAPINamespace)
							.put(Constants.HydraPrefix, Constants.HydraNamespace)
							.put("comment", Constants.Comment)
							.put("derivedFrom", Constants.DctNS + "source")
							.put("api", Constants.CovAPIPrefix + ":api")
							.put(Constants.OpenSearchGeoPrefix, Constants.OpenSearchGeoNamespace)
							.put(Constants.OpenSearchTimePrefix, Constants.OpenSearchTimeNamespace)
							.put("inCollection", ImmutableMap.of("@reverse", "hydra:member"))
							.build()
						));
		
		Builder coll = ImmutableMap.builder()
				.put("id", collectionUrl + queryString)
				.put("type", "CoverageCollection");
		if (subset.isConstrained) {
			coll.put("derivedFrom", ImmutableMap.of(
					"id", collectionUrl,
					"type", "CoverageCollection"
					));
		}
		coverageJson.put("inCollection", coll.build());
				
		if (subset.isConstrained) {
			coverageJson.put("derivedFrom", ImmutableMap.of(
					"id", coverageUrl,
					"type", "Coverage"
					));
		}
		Map apiIriTemplate = Hydra.getApiIriTemplate(coverageUrl, queryString, false, true);
		coverageJson.put("api", apiIriTemplate);
		
		Map json = coverageJson.build();
		
		return App.getCovJsonRepresentation(this, json);
	}
	
	void addLinkHeaders() {
		Series<Header> headers = this.getResponse().getHeaders();
		
		SubsetConstraint subset = new SubsetConstraint(getQuery());
		
		String datasetId = Reference.decode(getAttribute("datasetId"));
		String collectionUrl = getRootRef() + "/datasets/" + datasetId + "/coverages";
		String queryString = Constraint.getQueryString(subset.getCanonicalQueryParams());
		
		headers.add(new Header("Link", "<" + collectionUrl + queryString + ">; rel=\"collection\""));
	}
		
	static class UniformFeature {
		DiscreteFeature<?,?> feature;
		RectilinearGrid rectgrid;
		AbstractTransformedGrid projgrid;
		TimeAxis t;
		VerticalAxis z;
		String type;
		public UniformFeature(DiscreteFeature<?,?> feature) {
			this.feature = feature;
			this.type = FeatureTypes.getName(feature.getClass());
						
			// FIXME feature types should be interfaces
			
			// the following piece of code checks and uniformizes different feature types 
			if (feature instanceof GridFeature) {
				GridFeature gridFeature = (GridFeature) feature;
				GridDomain grid = gridFeature.getDomain();
				t = grid.getTimeAxis();
				z = grid.getVerticalAxis();
				
				if (grid.getHorizontalGrid() instanceof RectilinearGrid) {
					rectgrid = (RectilinearGrid) grid.getHorizontalGrid();
				} else if (grid.getHorizontalGrid() instanceof AbstractTransformedGrid) {
					// curvilinear grid or common projection
					projgrid = (AbstractTransformedGrid) grid.getHorizontalGrid();
				} else {
					throw new RuntimeException("Not supported: " + grid.getHorizontalGrid().getClass().getSimpleName());
				}
			} else if (feature instanceof ProfileFeature) {
				ProfileFeature profile = (ProfileFeature) feature;
				z = profile.getDomain();
				t = new TimeAxisImpl("time", ImmutableList.of(profile.getTime()));
				HorizontalPosition pos = profile.getHorizontalPosition();
				CoordinateSystem cs = pos.getCoordinateReferenceSystem().getCoordinateSystem();
				boolean isLongitudeX = cs.getAxis(0).getRangeMeaning() == RangeMeaning.WRAPAROUND;
				boolean isLongitudeY = cs.getAxis(1).getRangeMeaning() == RangeMeaning.WRAPAROUND;
				// TODO what are the bounds of the single cell here actually?
				//  -> does EDAL derive that from grids automatically?
				rectgrid = new RectilinearGridImpl(
						new ReferenceableAxisImpl("x", ImmutableList.of(pos.getX()), isLongitudeX),
						new ReferenceableAxisImpl("y", ImmutableList.of(pos.getY()), isLongitudeY),
						pos.getCoordinateReferenceSystem());
			} else {
				// TODO should probably say unsupported feature
				throw new UnsupportedDomainException(feature.getDomain());
			}
			
		}
	}
	
	static class UnsupportedDomainException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		Domain<?> domain;
		String info;
		public UnsupportedDomainException(Domain<?> domain, String info) {
			this.domain = domain;
			this.info = info;
		}
		public UnsupportedDomainException(Domain<?> domain) {
			this.domain = domain;
		}
	}
		
	private static Map unsupportedDomain(Domain<?> domain, String info) {
		return ImmutableMap.of(
				"info", info,
				"ERROR", "UNSUPPORTED: " + domain.getClass().getName()
				);
	}
		
	private static Map getParametersJson(FeatureMetadata meta, String rootUri) {		
		Builder params = ImmutableMap.builder();
		for (Parameter param : meta.rangeMeta.getParameters()) {
			params.put(param.getVariableId(), ParameterResource.getParamJson(meta.datasetId, param, rootUri).build());
		}
		return params.build();
	}
		
	private static Map getRangesJson(FeatureMetadata meta, Supplier<UniformFeature> uniFeatureFn, 
			boolean includeValues, SubsetConstraint subset, String rootUri) throws EdalException, IOException {
		String root = rootUri + "/datasets/" + meta.datasetId;
		Builder values = ImmutableMap.builder();
		
		String queryString = Constraint.getQueryString(subset.getCanonicalQueryParams());

		for (String paramId : meta.rangeMeta.getParameterIds()) {
			if (subset.params.isPresent() && !subset.params.get().contains(paramId)) {
				continue;
			}
			String rangeUrl = root + "/coverages/" + meta.featureId + "/range/" + paramId + queryString;
			Object rangeParam;
			
			if (includeValues) {
				rangeParam = CoverageRangeResource.getRangeJson(meta.datasetId, meta.featureId, paramId, subset);
			} else {
				rangeParam = rangeUrl;
			}
			
			values.put(paramId, rangeParam);
		}
		
		return values.build();
	}

}
