package com.czetsuyatech.web.security.jwt;

import com.czetsuyatech.web.security.SecurityConstants;
import com.czetsuyatech.web.security.config.CognitoJwtConfigData;
import com.czetsuyatech.web.security.exceptions.InvalidIdTokenException;
import com.czetsuyatech.web.security.exceptions.InvalidJwtIssuerException;
import com.czetsuyatech.web.security.identity.SimpleCtAccount;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class CognitoJwtTokenProcessor implements CtJwtTokenProcessor {

  private final ConfigurableJWTProcessor configurableJWTProcessor;
  private final CognitoJwtConfigData cognitoJwtConfigData;

  @Override
  public Authentication getAuthentication(HttpServletRequest request) throws Exception {

    String idToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(idToken)) {

      JWTClaimsSet claimsSet;

      claimsSet = configurableJWTProcessor.process(stripBearerToken(idToken), null);

      if (!isIssuedCorrectly(claimsSet)) {
        throw new InvalidJwtIssuerException(
            String.format("Issuer %s in JWT token doesn't match Cognito IDP %s", claimsSet.getIssuer(),
                cognitoJwtConfigData.getIssuerId()));
      }

      if (!isIdToken(claimsSet)) {
        throw new InvalidIdTokenException("JWT Token is not a valid Access or Id Token");
      }

      String username = claimsSet.getClaims().get(cognitoJwtConfigData.getUserNameField()).toString();

      if (username != null) {
        Optional<Object> optGroup = Optional.ofNullable(
            claimsSet.getClaims().get(cognitoJwtConfigData.getGroupField()));
        List<String> groups = Stream.of(optGroup.orElse(new ArrayList<>())).map(Object::toString).toList();

        CtSecurityContext securityContext = new CtSecurityContext(stripBearerToken(idToken), null);
        CtPrincipal<CtSecurityContext> ctPrincipal = new CtPrincipal(username, securityContext);
        SimpleCtAccount account = new SimpleCtAccount(ctPrincipal, Set.copyOf(groups), securityContext);

        return new CtAuthenticationToken(account);
      }
    }

    log.trace("No idToken found in HTTP Header");
    return null;
  }

  private String stripBearerToken(String token) {
    return token.startsWith(SecurityConstants.BEARER_PREFIX) ? token.substring(SecurityConstants.BEARER_PREFIX.length())
        : token;
  }

  private boolean isIssuedCorrectly(JWTClaimsSet claimsSet) {
    return claimsSet.getIssuer().equals(cognitoJwtConfigData.getIssuerId());
  }

  private boolean isIdToken(JWTClaimsSet claimsSet) {
    Object tokenUse = claimsSet.getClaim("token_use");
    return tokenUse.equals("id") || tokenUse.equals("access");
  }

// --Commented out by Inspection START (02/13/2022 10:11 AM):
//  private static <T, U> Set<U> convertCollection(Collection<T> from, Function<T, U> func) {
//    return from.stream().map(func).collect(Collectors.toSet());
//  }
// --Commented out by Inspection STOP (02/13/2022 10:11 AM)
}
