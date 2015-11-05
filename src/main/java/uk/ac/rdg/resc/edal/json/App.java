package uk.ac.rdg.resc.edal.json;

import java.util.List;
import java.util.Map;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.ClientInfo;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.data.Preference;
import org.restlet.data.Protocol;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Resource;
import org.restlet.routing.Router;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;

public class App extends Application {
	
    static MediaType JSONLD = new MediaType("application/ld+json");
    static MediaType CovJSON = new MediaType("application/prs.coverage+json");
    static MediaType CovJSONMsgpack = new MediaType("application/prs.coverage+msgpack");
    static MediaType CovJSONCBOR = new MediaType("application/prs.coverage+cbor");
    static MediaType GeoJSON = new MediaType("application/vnd.geo+json");
	
	public static void main(String[] args) throws Exception {
		Component component = new RestletComponent();
		component.getServers().add(Protocol.HTTP, 8081);
		component.start();
	}

	@Override
	public Restlet createInboundRoot() {
        getTunnelService().setExtensionsTunnel(true);
        
        // answer 406 if the Accept header matches none of the available variants
        getConnegService().setStrict(true);
        
        // clear common extensions so that we can define ours in preferred order
        // Note that this doesn't influence content negotiation, it is
        // just for serving JSON first for cases where */* was given as Accept.
        getMetadataService().clearExtensions();
                
        // CovJSON/JSON-LD is the default.
        // GeoJSON and binary CoverageJSON will only be delivered if explicitly requested.
        getMetadataService().addExtension("jsonld", JSONLD);
        getMetadataService().addExtension("covjson", CovJSON);
        getMetadataService().addExtension("geojson", GeoJSON);
        getMetadataService().addExtension("covcbor", CovJSONCBOR);
        getMetadataService().addExtension("covmsgpack", CovJSONMsgpack);
        getMetadataService().addExtension("cbor", CovJSONCBOR);
		
		Router router = new Router();
		router.attach("/datasets/{datasetId}/coverages/{coverageId}/range/{parameterId}",
				CoverageRangeResource.class);
		router.attach("/datasets/{datasetId}/coverages/{coverageId}/domain",
				CoverageDomainResource.class);
		router.attach("/datasets/{datasetId}/coverages/{coverageId}",
				CoverageResource.class);
		router.attach("/datasets/{datasetId}/coverages",
				CoverageCollectionResource.class);
		router.attach("/datasets/{datasetId}/params/{paramId}",
				ParameterResource.class);
		router.attach("/datasets/{datasetId}", DatasetResource.class);
		router.attach("/datasets", DatasetsResource.class);
		return router;
	}
	
	static List<MediaType> jsonTypes = ImmutableList.of(
			CovJSON, JSONLD, GeoJSON, MediaType.APPLICATION_JSON
			);
			
	
	public static boolean acceptsJSON(Resource resource) {
		ClientInfo info = resource.getClientInfo();
		Header accept = resource.getRequest().getHeaders().getFirst("Accept");
		if (accept == null) {
			System.err.println("accept header not found!");
			return false;
		}
		for (Preference<MediaType> pref : info.getAcceptedMediaTypes()) {
			if (jsonTypes.contains(pref.getMetadata())) {
				// when requested by extension, then Restlet modifies the accepted media types
				// but we want to know if the client actually sent the header or not
				if (accept.getValue().contains(pref.getMetadata().getName())) {
					return true;
				}	
			}
		}
		return false;
	}
	
	public static Representation getCovJsonRepresentation(Resource resource, Map<String,?> json) {
		MediaType type = resource.getClientInfo().getAcceptedMediaTypes().get(0).getMetadata();
		
		if (type.equals(App.CovJSONMsgpack)) {
			Representation r = new MessagePackRepresentation(json);
			r.setMediaType(App.CovJSONMsgpack);
			return r;
		} /*else if (type.equals(App.CovJSONCBOR)) {
			
		}*/ else /*if (type.equals(App.CovJSON))*/ {
			JacksonRepresentation<Map<String,?>> r = new JacksonRepresentation<>(json);
			r.setMediaType(App.CovJSON);
			if (!App.acceptsJSON(resource)) {
				r.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			}
			return r;
		}
	}
}