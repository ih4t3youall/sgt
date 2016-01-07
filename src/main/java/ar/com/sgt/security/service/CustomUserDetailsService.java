package ar.com.sgt.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.sgt.persistence.dao.IUserProfileDAO;
import ar.com.sgt.security.model.User;
import ar.com.sgt.security.model.UserProfile;
import ar.com.sgt.services.IUserService;

@Service("customUserDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserService userService;
	
	@Autowired 
	private IUserProfileDAO userProfileDAO;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String ssoId)
			throws UsernameNotFoundException {
		User user = userService.findBySso(ssoId);
		System.out.println("User : "+user);
		if(user==null){
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		 List<UserProfile> findUserProfileByUserID = userProfileDAO.findUserProfileByUserID(user.getId());
		 Set<UserProfile> profileSet = new HashSet<UserProfile>();
		 
		 for (UserProfile userProfile : findUserProfileByUserID) {
			
			 profileSet.add(userProfile);
			 
		}
		 
		user.setUserProfiles(profileSet);
		org.springframework.security.core.userdetails.User SUsuario = new org.springframework.security.core.userdetails.User(user.getSsoId(), user.getPassword(), 
				 user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));
		
		
		
		return SUsuario;
	}

	
	private List<GrantedAuthority> getGrantedAuthorities(User user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(UserProfile userProfile : user.getUserProfiles()){
			System.out.println("UserProfile : "+userProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType().trim()));
		}
		System.out.print("authorities :"+authorities);
		return authorities;
	}
	
}
