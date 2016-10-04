package com.tincore.auth.server.form;

import com.tincore.auth.server.domain.User;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2016-09-23T13:54:34+0200",

    comments = "version: 1.1.0.CR1, compiler: javac, environment: Java 1.8.0_101 (Oracle Corporation)"

)

@Component

public class FormMapperImpl implements FormMapper {

    @Autowired

    private FormMapperUtil formMapperUtil;

    @Override

    public UserForm toUserForm(User user) {

        if ( user == null ) {

            return null;
        }

        UserForm userForm = new UserForm();

        userForm.setUsername( user.getUsername() );

        userForm.setFirstName( user.getFirstName() );

        userForm.setLastName( user.getLastName() );

        userForm.setEnabled( user.isEnabled() );

        userForm.setAccountLocked( user.isAccountLocked() );

        userForm.setAccountExpired( user.isAccountExpired() );

        userForm.setCredentialsExpired( user.isCredentialsExpired() );

        userForm.setUserAuthorities( formMapperUtil.map( user.getUserAuthorities() ) );

        return userForm;
    }

    @Override

    public UserEditForm toUserEditForm(User user) {

        if ( user == null ) {

            return null;
        }

        UserEditForm userEditForm = new UserEditForm();

        userEditForm.setUsername( user.getUsername() );

        userEditForm.setFirstName( user.getFirstName() );

        userEditForm.setLastName( user.getLastName() );

        userEditForm.setEnabled( user.isEnabled() );

        userEditForm.setAccountLocked( user.isAccountLocked() );

        userEditForm.setAccountExpired( user.isAccountExpired() );

        userEditForm.setCredentialsExpired( user.isCredentialsExpired() );

        userEditForm.setUserAuthorities( formMapperUtil.map( user.getUserAuthorities() ) );

        return userEditForm;
    }

    @Override

    public void update(UserEditForm userForm, User user) {

        if ( userForm == null ) {

            return;
        }

        user.setAccountExpired( userForm.isAccountExpired() );

        user.setAccountLocked( userForm.isAccountLocked() );

        user.setCredentialsExpired( userForm.isCredentialsExpired() );

        user.setEnabled( userForm.isEnabled() );

        user.setFirstName( userForm.getFirstName() );

        user.setLastName( userForm.getLastName() );
    }
}

