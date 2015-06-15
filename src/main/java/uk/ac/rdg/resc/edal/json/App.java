package uk.ac.rdg.resc.edal.json;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

import com.google.common.collect.ImmutableSet;

public class App extends Application {
	public static void main(String[] args) throws Exception {
		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 8182);
		App app = new App();
		app.getEncoderService().setEnabled(true);
		CorsService corsService = new CorsService();
		corsService.setAllowedOrigins(ImmutableSet.of("*"));
		corsService.setAllowedCredentials(true);
		app.getServices().add(corsService);
		component.getDefaultHost().attach(app);
		component.start();
	}

	@Override
	public Restlet createInboundRoot() {
        getTunnelService().setExtensionsTunnel(true);
        
        // clear common extensions so that we can define ours in preferred order
        // Note that this doesn't influence content negotiation, it is
        // just for serving JSON by default if no matching Accept headers are given.
        getMetadataService().clearExtensions();
        getMetadataService().addExtension("json", MediaType.APPLICATION_JSON);
        getMetadataService().addExtension("msgpack", MessagePackRepresentation.APPLICATION_MSGPACK);
		
		Router router = new Router();
		router.attach("/datasets/{datasetId}/features/{featureId}/range/{parameterId}",
				FeatureParameterRangeResource.class);
		router.attach("/datasets/{datasetId}/features/{featureId}",
				FeatureResource.class);
		router.attach("/datasets/{datasetId}/features",
				FeaturesResource.class);
		router.attach("/datasets/{datasetId}", DatasetResource.class);
		// router.attach("/datasets",
		// DatasetsResource.class);
		return router;
	}
}