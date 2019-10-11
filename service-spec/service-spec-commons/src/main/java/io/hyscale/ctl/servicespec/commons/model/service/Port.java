package io.hyscale.ctl.servicespec.commons.model.service;

public class Port {

	private String port;
	private HealthCheck healthCheck;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public HealthCheck getHealthCheck() {
		return healthCheck;
	}

	public void setHealthCheck(HealthCheck healthCheck) {
		this.healthCheck = healthCheck;
	}

	public static class HealthCheck {

		private String httpPath;

		public String getHttpPath() {
			return httpPath;
		}

		public void setHttpPath(String httpPath) {
			this.httpPath = httpPath;
		}
	}
}