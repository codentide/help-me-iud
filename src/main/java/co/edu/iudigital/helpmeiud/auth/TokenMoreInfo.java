package co.edu.iudigital.helpmeiud.auth;


import co.edu.iudigital.helpmeiud.dtos.users.UserRequestDTO;
import co.edu.iudigital.helpmeiud.models.User;
import co.edu.iudigital.helpmeiud.services.interfaces.IUserService;
import co.edu.iudigital.helpmeiud.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Adicionar info del usuario al token
 * o cualquier otra
 * luego se registra en AuthorizationServerConfig
 * @author JULIOCESARMARTINEZ
 *
 */
@Component
public class TokenMoreInfo implements TokenEnhancer {

	@Autowired
	private IUserService iUserService;

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = userMapper.toEntity(iUserService.readUserByUsername(authentication.getName()));
		Map<String, Object> info = new HashMap<>();
		info.put("user_id", ""+user.getId());
		info.put("enabled", user.getEnabled());
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
