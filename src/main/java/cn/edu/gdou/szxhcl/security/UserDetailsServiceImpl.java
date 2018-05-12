package cn.edu.gdou.szxhcl.security;

import cn.edu.gdou.szxhcl.dao.UserDao;
import cn.edu.gdou.szxhcl.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findFirstByUsername(username);
        if(user != null) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            String role = user.getRole();
            if(!StringUtils.isEmpty(role)) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
        } else {
            throw new UsernameNotFoundException(String.format("Username: %s", username));
        }
    }
}
