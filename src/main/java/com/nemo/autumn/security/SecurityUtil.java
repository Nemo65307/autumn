package com.nemo.autumn.security;

import com.nemo.autumn.exception.AccessForbiddenException;
import com.nemo.autumn.Constants;
import com.nemo.autumn.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

    public static CurrentUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CurrentUser) {
            return ((CurrentUser) principal);
        } else {
            return null;
        }
    }

    public static String getCurrentUserId() {
        CurrentUser currentProfile = getCurrentUser();
        return currentProfile != null ? currentProfile.getId() : null;
    }

    public static void authenticate(User user) {
        CurrentUser currentProfile = new CurrentUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                currentProfile, currentProfile.getPassword(),
                currentProfile.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static boolean isCurrentProfileAuthenticated() {
        return getCurrentUser() != null;
    }

    public static String getCurrentUserRole(User user) {
        if (user.getRole().getName().equalsIgnoreCase("admin")) {
            return Constants.ROLE_ADMIN;
        } else if (user.getRole().getName().equalsIgnoreCase("user")) {
            return Constants.ROLE_USER;
        }
        throw new AccessForbiddenException("User has an unknown role!");
    }

    public static String getRoleHomePage(String role) {
        if (role.equals(Constants.ROLE_ADMIN)) {
            return "admin/users-list";
        }
        if (role.equals(Constants.ROLE_USER)) {
            return "home";
        }
        return null;
    }

}
