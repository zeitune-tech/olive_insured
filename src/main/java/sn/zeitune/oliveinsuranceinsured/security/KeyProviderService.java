package sn.zeitune.oliveinsuranceinsured.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsuranceinsured.enums.UserRole;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class KeyProviderService {

    @Value("${jwt.user.public-key}")
    private Resource publicRes;

    @Value("${jwt.admin.public-key}")
    private Resource adminPublicRes;


    public PublicKey getPublicKey(UserRole role) throws Exception {
        Resource res = role.equals(UserRole.ADMIN) ? adminPublicRes : publicRes;

        String key = new String(res.getInputStream().readAllBytes());
        key = key.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

}
