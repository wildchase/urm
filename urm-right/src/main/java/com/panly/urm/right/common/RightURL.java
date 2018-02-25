package com.panly.urm.right.common;


public class RightURL {

	/**
	 * The protocol to use (ftp, http, nntp, ... etc.) .
	 * 
	 * @serial
	 */
	private String protocol;

	/**
	 * The host name to connect to.
	 * 
	 * @serial
	 */
	private String host;

	/**
	 * The protocol port to connect to.
	 * 
	 * @serial
	 */
	private int port = -1;

	/**
	 * The specified file name on that host. <code>file</code> is defined as
	 * <code>path[?query]</code>
	 * 
	 * @serial
	 */
	private String file;

	/**
	 * The query part of this URL.
	 */
	private transient String query;

	/**
	 * The authority part of this URL.
	 * 
	 * @serial
	 */
	private String authority;
	
	/**
	 * The path part of this URL.
	 */
	private transient String path;

	/**
	 * The userinfo part of this URL.
	 */
	private transient String userInfo;
	
	private transient String username;
	
	private transient String password;

	public RightURL(String spec) {
		int i, limit, c;
		int start = 0;
		String newProtocol = null;
		boolean aRef = false;

		limit = spec.trim().length();

		for (i = start; !aRef && (i < limit) && ((c = spec.charAt(i)) != '/'); i++) {
			if (c == ':') {
				String s = spec.substring(start, i).toLowerCase();
				newProtocol = s;
				start = i + 1;
				break;
			}
		}

		protocol = newProtocol;

		boolean isRelPath = false;
		boolean queryOnly = false;

		// FIX: should not assume query if opaque
		// Strip off the query part
		if (start < limit) {
			int queryStart = spec.indexOf('?');
			queryOnly = queryStart == start;
			if ((queryStart != -1) && (queryStart < limit)) {
				query = spec.substring(queryStart + 1, limit);
				if (limit > queryStart)
					limit = queryStart;
				spec = spec.substring(0, queryStart);
			}
		}

		i = 0;
		// Parse the authority part if any
		boolean isUNCName = (start <= limit - 4) && (spec.charAt(start) == '/') && (spec.charAt(start + 1) == '/')
				&& (spec.charAt(start + 2) == '/') && (spec.charAt(start + 3) == '/');
		if (!isUNCName && (start <= limit - 2) && (spec.charAt(start) == '/') && (spec.charAt(start + 1) == '/')) {
			start += 2;
			i = spec.indexOf('/', start);
			if (i < 0) {
				i = spec.indexOf('?', start);
				if (i < 0)
					i = limit;
			}

			host = authority = spec.substring(start, i);

			int ind = authority.indexOf('@');
			if (ind != -1) {
				userInfo = authority.substring(0, ind);
				host = authority.substring(ind + 1);
				
				String[] strs = userInfo.split(":");
				if(strs!=null&&strs.length==2){
					username = strs[0];
					password = strs[1];
				}
				
			} else {
				userInfo = null;
			}
			if (host != null) {
				// If the host is surrounded by [ and ] then its an IPv6
				// literal address as specified in RFC2732
				if (host.length() > 0 && (host.charAt(0) == '[')) {
					if ((ind = host.indexOf(']')) > 2) {

						String nhost = host;
						host = nhost.substring(0, ind + 1);
						port = -1;
						if (nhost.length() > ind + 1) {
							if (nhost.charAt(ind + 1) == ':') {
								++ind;
								// port can be null according to RFC2396
								if (nhost.length() > (ind + 1)) {
									port = Integer.parseInt(nhost.substring(ind + 1));
								}
							} else {
								throw new IllegalArgumentException("Invalid authority field: " + authority);
							}
						}
					} else {
						throw new IllegalArgumentException("Invalid authority field: " + authority);
					}
				} else {
					ind = host.indexOf(':');
					port = -1;
					if (ind >= 0) {
						// port can be null according to RFC2396
						if (host.length() > (ind + 1)) {
							port = Integer.parseInt(host.substring(ind + 1));
						}
						host = host.substring(0, ind);
					}
				}
			} else {
				host = "";
			}
			if (port < -1)
				throw new IllegalArgumentException("Invalid port number :" + port);
			start = i;
			// If the authority is defined then the path is defined by the
			// spec only; See RFC 2396 Section 5.2.4.
			if (authority != null && authority.length() > 0)
				path = "";
		}

		if (host == null) {
			host = "";
		}

		// Parse the file path if any
		if (start < limit) {
			if (spec.charAt(start) == '/') {
				path = spec.substring(start, limit);
			} else if (path != null && path.length() > 0) {
				isRelPath = true;
				int ind = path.lastIndexOf('/');
				String seperator = "";
				if (ind == -1 && authority != null)
					seperator = "/";
				path = path.substring(0, ind + 1) + seperator + spec.substring(start, limit);

			} else {
				String seperator = (authority != null) ? "/" : "";
				path = seperator + spec.substring(start, limit);
			}
		} else if (queryOnly && path != null) {
			int ind = path.lastIndexOf('/');
			if (ind < 0)
				ind = 0;
			path = path.substring(0, ind) + "/";
		}
		if (path == null)
			path = "";

		if (isRelPath) {
			// Remove embedded /./
			while ((i = path.indexOf("/./")) >= 0) {
				path = path.substring(0, i) + path.substring(i + 2);
			}
			// Remove embedded /../ if possible
			i = 0;
			while ((i = path.indexOf("/../", i)) >= 0) {
				/*
				 * A "/../" will cancel the previous segment and itself, unless
				 * that segment is a "/../" itself i.e. "/a/b/../c" becomes
				 * "/a/c" but "/../../a" should stay unchanged
				 */
				if (i > 0 && (limit = path.lastIndexOf('/', i - 1)) >= 0 && (path.indexOf("/../", limit) != 0)) {
					path = path.substring(0, limit) + path.substring(i + 3);
					i = 0;
				} else {
					i = i + 3;
				}
			}
			// Remove trailing .. if possible
			while (path.endsWith("/..")) {
				i = path.indexOf("/..");
				if ((limit = path.lastIndexOf('/', i - 1)) >= 0) {
					path = path.substring(0, limit + 1);
				} else {
					break;
				}
			}
			// Remove starting .
			if (path.startsWith("./") && path.length() > 2)
				path = path.substring(2);

			// Remove trailing .
			if (path.endsWith("/."))
				path = path.substring(0, path.length() - 1);
		}
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return toString(this);
	}
	
	public String toString(RightURL u){
		// pre-compute length of StringBuffer
        int len = u.getProtocol().length() + 1;
        if (u.getAuthority() != null && u.getAuthority().length() > 0)
            len += 2 + u.getAuthority().length();
        if (u.getPath() != null) {
            len += u.getPath().length();
        }
        if (u.getQuery() != null) {
            len += 1 + u.getQuery().length();
        }
        StringBuffer result = new StringBuffer(len);
        result.append(u.getProtocol());
        result.append(":");
        if (u.getAuthority() != null && u.getAuthority().length() > 0) {
            result.append("//");
            result.append(u.getAuthority());
        }
        if (u.getPath() != null) {
            result.append(u.getPath());
        }
        if (u.getQuery() != null) {
            result.append('?');
            result.append(u.getQuery());
        }
        return result.toString();
	}
	
	public static void main(String[] args) throws Exception {
		RightURL u = new RightURL("mysql://ybp:12345@10.37.149.48:3306/ybp");
		
//		RightURL u = new RightURL("jdbc:mysql://10.37.149.48:3306/rpt");
		System.out.println("jdbc:"+u.getProtocol()+"://"+u.getHost()+":"+u.getPort()+"/"+u.getPath());
//		System.out.println(u);
		
//		jdbc:mysql://10.37.149.48:3306/rpt
	}
	
}
