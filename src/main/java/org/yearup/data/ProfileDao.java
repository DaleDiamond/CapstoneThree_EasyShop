package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    static Profile update(Profile profile) {
        return profile;
    }

    Profile create(Profile profile);

    Profile getByUserId(int userId);
}
