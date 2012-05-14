package com.mime.qweibo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
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

import com.mime.qweibo.utils.QHttpUtil;

public class QHttpClient {

//	private static final String CLIENT_VERSION_HEADER = "User-Agent";
//	private static final String DEFAULT_CLIENT_VERSION = "com.znisea.linju";
	private static final int CONNECTION_TIMEOUT = 20000;
	private final DefaultHttpClient mHttpClient;
	private static final int TIMEOUT = 10;
//	private String mClientVersion;
	public QHttpClient(DefaultHttpClient httpClient) {
		mHttpClient = httpClient;
//		 mClientVersion = DEFAULT_CLIENT_VERSION;
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
    public HttpGet createHttpGet(String url, String queryString) {

		if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
		}
        HttpGet httpGet = new HttpGet(url);
        httpGet.getParams().setParameter("http.socket.timeout",
				new Integer(CONNECTION_TIMEOUT));
//        httpGet.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        return httpGet;
    }
	
	/**
     * execute a HttpGet
     * @param url
     * @param nameValuePairs
     * @return
     * @throws Exception
     */
    public String doHttpGet(String url, String  queryString) throws Exception{
    	 HttpGet httpGet = createHttpGet(url, queryString);

         HttpResponse response = executeHttpRequest(httpGet);
//         return httpGet.getResponseBodyAsString();

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
	 * Using GET method.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpGet(String url, String queryString) throws Exception {
		String responseData = null;

		if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
		}

		HttpClient httpClient = new HttpClient();
		GetMethod httpGet = new GetMethod(url);
		httpGet.getParams().setParameter("http.socket.timeout",
				new Integer(CONNECTION_TIMEOUT));

		try {
			int statusCode = httpClient.executeMethod(httpGet);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpGet Method failed: "
						+ httpGet.getStatusLine());
			}
			// Read the response body.
			responseData = httpGet.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpGet.releaseConnection();
			httpClient = null;
		}

		return responseData;
	}

	/**
	 * Using POST method.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpPost(String url, String queryString) throws Exception {
		String responseData = null;
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		httpPost.addParameter("Content-Type",
				"application/x-www-form-urlencoded");
		httpPost.getParams().setParameter("http.socket.timeout",
				new Integer(CONNECTION_TIMEOUT));
		if (queryString != null && !queryString.equals("")) {
			httpPost.setRequestEntity(new ByteArrayRequestEntity(queryString
					.getBytes()));
		}

		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpPost Method failed: "
						+ httpPost.getStatusLine());
			}
			responseData = httpPost.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseData;
	}

	/**
	 * Using POST method with multiParts.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @param files
	 *            The list of image files
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpPostWithFile(String url, String queryString,
			List<QParameter> files) throws Exception {

		String responseData = null;
		url += '?' + queryString;
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		try {
			List<QParameter> listParams = QHttpUtil
					.getQueryParameters(queryString);
			int length = listParams.size() + (files == null ? 0 : files.size());
			Part[] parts = new Part[length];
			int i = 0;
			for (QParameter param : listParams) {
				parts[i++] = new StringPart(param.mName,
						QHttpUtil.formParamDecode(param.mValue), "UTF-8");
			}
			for (QParameter param : files) {
				File file = new File(param.mValue);
				parts[i++] = new FilePart(param.mName, file.getName(), file,
						QHttpUtil.getContentType(file), "UTF-8");
			}

			httpPost.setRequestEntity(new MultipartRequestEntity(parts,
					httpPost.getParams()));

			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpPost Method failed: "
						+ httpPost.getStatusLine());
			}
			responseData = httpPost.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseData;
	}

}
