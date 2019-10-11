package io.hyscale.ctl.generator.services.predicates;

import com.fasterxml.jackson.core.type.TypeReference;
import io.hyscale.ctl.commons.exception.HyscaleException;
import io.hyscale.ctl.generator.services.provider.PropsProvider;
import io.hyscale.ctl.generator.services.provider.SecretsProvider;
import io.hyscale.ctl.servicespec.commons.fields.HyscaleSpecFields;
import io.hyscale.ctl.servicespec.commons.model.service.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Predicate;

public class ManifestPredicates {

	public static Predicate<ServiceSpec> getVolumesPredicate() {
		return serviceSpec -> {
			TypeReference<List<Volume>> volumesList = new TypeReference<List<Volume>>() {
			};
			List<Volume> volumes = null;
			try {
				volumes = serviceSpec.get("volumes", volumesList);
			} catch (HyscaleException e) {
				return false;
			}
			if (volumes != null && !volumes.isEmpty()) {
				return true;
			}
			return false;
		};
	}

	public static Predicate<ServiceSpec> getPortsPredicate() {
		return serviceSpec -> {
			TypeReference<List<Port>> portsList = new TypeReference<List<Port>>() {
			};
			List<Port> portList = null;
			try {
				portList = serviceSpec.get(HyscaleSpecFields.ports, portsList);
			} catch (HyscaleException e) {
				return false;
			}
			if (portList != null && !portList.isEmpty()) {
				return true;
			}
			return false;
		};
	}

	public static Predicate<ServiceSpec> getPropsPredicate() {
		return serviceSpec -> {
			Props props = null;
			try {
				props = PropsProvider.getProps(serviceSpec);
			} catch (HyscaleException e) {
				return false;
			}
			if (props != null && props.getProps() != null && !props.getProps().isEmpty()) {
				return true;
			}
			return false;
		};
	}

	public static Predicate<ServiceSpec> getSecretsPredicate() {
		return serviceSpec -> {
			Secrets secrets = null;
			try {
				secrets = SecretsProvider.getSecrets(serviceSpec);
			} catch (HyscaleException e) {
				return false;
			}
			if (secrets == null || (secrets.getSecretKeys() == null && secrets.getSecretsMap() == null)) {
				return false;
			}
			if (secrets.getSecretsMap() != null && !secrets.getSecretsMap().isEmpty()) {
				return true;
			}
			if (secrets.getSecretKeys() != null && !secrets.getSecretKeys().isEmpty()) {
				return false;
			}
			return false;
		};
	}

	public static Predicate<ServiceSpec> getSecretsEnvPredicate() {
		return serviceSpec -> {
			Secrets secrets = null;
			try {
				secrets = SecretsProvider.getSecrets(serviceSpec);
			} catch (HyscaleException e) {
				return false;
			}
			if (secrets == null || (secrets.getSecretKeys() == null && secrets.getSecretsMap() == null)) {
				return false;
			}
			if (secrets.getSecretKeys() != null && !secrets.getSecretKeys().isEmpty()) {
				return true;
			}
			if (secrets.getSecretsMap() != null && !secrets.getSecretsMap().isEmpty()) {
				return true;
			}
			return false;
		};
	}

	public static Predicate<ServiceSpec> haveConfigmapVolume() {
		return serviceSpec -> {
			String propsVolumePath = null;
			try {
				propsVolumePath = serviceSpec.get(HyscaleSpecFields.propsVolumePath, String.class);
			} catch (HyscaleException e) {
				return false;
			}
			if (!StringUtils.isBlank(propsVolumePath)) {
				return true;
			}
			return false;
		};
	}

	public static Predicate<ServiceSpec> haveSecretsVolume() {
		return serviceSpec -> {
			String secretsVolumePath = null;
			try {
				secretsVolumePath = serviceSpec.get(HyscaleSpecFields.secretsVolumePath, String.class);
			} catch (HyscaleException e) {
				return false;
			}
			if (!StringUtils.isBlank(secretsVolumePath)) {
				return true;
			}
			return false;
		};
	}
}