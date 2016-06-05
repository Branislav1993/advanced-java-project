package rs.fon.parlament.filters;

import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

@Provider
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {

		response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
		response.getHttpHeaders().add("Access-Control-Allow-Headers",
				"origin, content-type, accept, authorization, X-Requested-With");
		response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
		response.getHttpHeaders().add("Access-Control-Allow-Methods", "PUT,GET,POST,DELETE,OPTIONS,HEAD");

//		if(response.getHttpHeaders().get("Allow") != null) {
//			List<Object> lista = new ArrayList<>();
//			lista.add("HEAD,DELETE,GET,OPTIONS,PUT,POST");
//			response.getHttpHeaders().put("Allow", lista);
//		}
//		System.out.println(response.getHttpHeaders().get("Allow"));

		return response;
	}

}
