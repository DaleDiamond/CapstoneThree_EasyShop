package org.yearup.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileDao profileDao;

    public ProfileController(ProfileDao profileDao){
        this.profileDao = profileDao;
    }

    @GetMapping
    public ResponseEntity<Profile>getProfile() {
        int userId = getCurrentUserId();

        Profile profile = profileDao.getByUserId(userId);
        if(profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
        }

        profile.setUserId(userId);
        Profile updatedProfile = ProfileDao.update(profile);

        return ResponseEntity.ok(updatedProfile);
    }
    private int getCurrentUserId() {
        User user = getCurrentUser();
        if (user != null){
            return user.getId();
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }
    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }else{
            return null;
        }
    }
}