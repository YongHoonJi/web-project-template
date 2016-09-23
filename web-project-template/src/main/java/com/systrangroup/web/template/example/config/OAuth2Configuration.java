package com.systrangroup.web.template.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.systrangroup.web.template.example.service.UserAuthenticationService;

/**
 * 아래 url의 용도에 대해 샘플링 해보자
 * /oauth/authorize (인가 엔드포인트)
 * /oauth/token (토큰 엔드포인트)
 * /oauth/confirm_access (사용자가 허가의 승인을 확인하는 POST 요청)
 * /oauth/error (인가 서버에서 에러를 보여줄 때 사용)
 * /oauth/check_token (액세스 토큰을 복호화 하기 위해 리소스 서버에서 사용)
 * /oauth/token_key (JWT 토큰을 사용하는 경우 토큰 검증을 위한 공개키를 노출)가 있다.
 */
@Configuration
public class OAuth2Configuration{

	private static final String RESOURCE_ID = "mat-rest-api";

	/**
	 * API 리소스 서버 설정
	 */
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.headers().frameOptions().disable();
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.authorizeRequests().antMatchers("/mat/v1.0/**", "/auth/**").authenticated();
		}

	}

	/**
	 * oauth 권한 인증 서버
	 */
	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {

		private TokenStore tokenStore = new InMemoryTokenStore();

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Autowired
		private UserAuthenticationService userDetailsService;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints
				.tokenStore(this.tokenStore)
				.authenticationManager(this.authenticationManager)
				.userDetailsService(userDetailsService);
		}
		
	    @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            //oauthServer.realm("oauth/client");
	    	security.allowFormAuthenticationForClients();
        }
	    
		
		/* 
		 * client에 대한 접근 정보 설정
		 * @다수 client가 접근하는 경우 인메모리가 아닌 database를 고려
		 */
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients
				.inMemory()
					.withClient("mat-web") 					// 접근 클라언트 ID
						.authorizedGrantTypes("password") 	// 인증타입
						.authorities("USER")				// 접근 권한
						.scopes("read", "write")
						.resourceIds(RESOURCE_ID)			// 리소스 식별자
						.secret("systran-6840")				// 클라언트에 발급된 비밀번호
						.accessTokenValiditySeconds(3600);	// 토큰 유효시간(초)
		}

		/**
		 * 발급된 인증 토큰을 저장소에 저장
		 * @return
		 */
		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(this.tokenStore);
			return tokenServices;
		}
		
		@Bean
		@Primary
		public TokenStore tokenStore() {
		    return this.tokenStore;
		}

	}

}
