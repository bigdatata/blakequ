package cm.exchange.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


/**
 * http get and post method
 * @author Administrator
 * <b>#BaseListActivity#
 */
public class HttpClient{

	protected static final Logger LOG = Logger.getLogger(HttpClient.class.getCanonicalName());

    private static final String DEFAULT_CLIENT_VERSION = "com.znisea.linju";
    private static final String CLIENT_VERSION_HEADER = "User-Agent";
    private static final int TIMEOUT = 10;

    private final DefaultHttpClient mHttpClient;
    private final String mClientVersion;

    /**
     * Http utility
     * @param httpClient	
     * @param clientVersion allow null
     */
    public HttpClient(DefaultHttpClient httpClient, String clientVersion) {
        mHttpClient = httpClient;
        if (clientVersion != null) {
            mClientVersion = clientVersion;
        } else {
            mClientVersion = DEFAULT_CLIENT_VERSION;
        }
    }

    /**
     * execute a HttpPost
     * @return string
     */
    public String doHttpPost(String url, NameValuePair... nameValuePairs)
            throws Exception {
        HttpPost httpPost = createHttpPost(url, nameValuePairs);

        HttpResponse response = executeHttpRequest(httpPost);

        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                try {
                    return EntityUtils.toString(response.getEntity());
                } catch (ParseException e) {
                    throw new Exception(e.getMessage());
                }

            case 401:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            case 404:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            default:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());
        }
    }
    
    /**
     * execute a HttpPost
     * @return InputStream
     */
    public InputStream doHttpPost2(String url, NameValuePair... nameValuePairs)
            throws Exception {
        HttpPost httpPost = createHttpPost(url, nameValuePairs);

        HttpResponse response = executeHttpRequest(httpPost);

        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                try {
                    return response.getEntity().getContent();
                } catch (ParseException e) {
                    throw new Exception(e.getMessage());
                }

            case 401:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            case 404:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            default:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());
        }
    }
    
    /**
     * execute a HttpGet
     * @param url
     * @param nameValuePairs
     * @return
     * @throws Exception
     */
    public String doHttpGet(String url, NameValuePair... nameValuePairs ) throws Exception{
    	 HttpGet httpGet = createHttpGet(url, nameValuePairs);

         HttpResponse response = executeHttpRequest(httpGet);

         switch (response.getStatusLine().getStatusCode()) {
             case 200:
                 try {
                     return EntityUtils.toString(response.getEntity());
                 } catch (ParseException e) {
                     throw new Exception(e.getMessage());
                 }

             case 401:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());

             case 404:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());

             default:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());
         }
    }
    
    /**
     * execute a HttpGet
     * @param url
     * @param nameValuePairs
     * @return
     * @throws Exception
     */
    public InputStream doHttpGet2(String url, NameValuePair... nameValuePairs ) throws Exception{
    	 HttpGet httpGet = createHttpGet(url, nameValuePairs);

         HttpResponse response = executeHttpRequest(httpGet);

         switch (response.getStatusLine().getStatusCode()) {
             case 200:
                 try {
                     return response.getEntity().getContent();
                 } catch (ParseException e) {
                     throw new Exception(e.getMessage());
                 }

             case 401:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());

             case 404:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());

             default:
                 response.getEntity().consumeContent();
                 throw new Exception(response.getStatusLine().toString());
         }
    }

    /**
     * execute() an httpRequest catching exceptions and returning null instead.
     *
     * @param httpRequest
     * @return
     * @throws IOException
     */
    public HttpResponse executeHttpRequest(HttpRequestBase httpRequest) throws IOException {
        try {
            mHttpClient.getConnectionManager().closeExpiredConnections();
            return mHttpClient.execute(httpRequest);
        } catch (IOException e) {
            httpRequest.abort();
            throw e;
        }
    }

    /**
     * create HttpGet
     */
    public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs) {
        String query = URLEncodedUtils.format(createParams(nameValuePairs), HTTP.UTF_8);
        HttpGet httpGet = new HttpGet(url + "?" + query);
        httpGet.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        return httpGet;
    }

    /**
     * create HttpPost
     */
    public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(createParams(nameValuePairs), HTTP.UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new IllegalArgumentException("Unable to encode http parameters.");
        }
        return httpPost;
    }

    /**
     * create parameter
     * @param nameValuePairs
     * @return
     */
    private List<NameValuePair> createParams(NameValuePair... nameValuePairs) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (int i = 0; i < nameValuePairs.length; i++) {
            NameValuePair param = nameValuePairs[i];
            if (param.getValue() != null) {
                params.add(param);
            }
        }
        return params;
    }

    /**
     * Create a thread-safe client. This client does not do redirecting, to allow us to capture
     * correct "error" codes.
     *
     * @return HttpClient
     */
    public static final DefaultHttpClient createHttpClient() {
        // Sets up the http part of the service.
        final SchemeRegistry supportedSchemes = new SchemeRegistry();

        // Register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        final SocketFactory sf = PlainSocketFactory.getSocketFactory();
        supportedSchemes.register(new Scheme("http", sf, 80));

        // Set some client http client parameter defaults.
        final HttpParams httpParams = createHttpParams();
        HttpClientParams.setRedirecting(httpParams, false);

        final ClientConnectionManager ccm = new ThreadSafeClientConnManager(httpParams,
                supportedSchemes);
        return new DefaultHttpClient(ccm, httpParams);
    }

    /**
     * Create the default HTTP protocol parameters.
     */
    private static final HttpParams createHttpParams() {
        final HttpParams params = new BasicHttpParams();

        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSoTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        return params;
    }

	public Object getConnectionManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpResponse execute(HttpPost httppost) {
		// TODO Auto-generated method stub
		return null;
	}
}
