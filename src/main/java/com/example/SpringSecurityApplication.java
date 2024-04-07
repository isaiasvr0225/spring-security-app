package com.example;

import com.example.persistence.entity.PermissionEntity;
import com.example.persistence.entity.RoleEntity;
import com.example.persistence.entity.RoleEnum;
import com.example.persistence.entity.UserEntity;
import com.example.persistence.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;


@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserEntityRepository userEntityRepository) {
        return args -> {
            // Populating the database with data

            // Creating the permission
            var createPermission = PermissionEntity
                                        .builder()
                                        .name("CREATE")
                                        .build();

            var readPermission = PermissionEntity
                                        .builder()
                                        .name("READ")
                                        .build();

            var updatePermission = PermissionEntity
                                        .builder()
                                        .name("UPDATE")
                                        .build();

            var deletePermission = PermissionEntity
                                        .builder()
                                        .name("DELETE")
                                        .build();

            // Creating the roles
            var adminRole = RoleEntity
                    .builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionSet(Set.of(createPermission,
                                          readPermission,
                                          updatePermission,
                                          deletePermission))
                    .build();

            var userRole = RoleEntity
                    .builder()
                    .roleEnum(RoleEnum.USER)
                    .permissionSet(Set.of(createPermission,
                                          readPermission))
                    .build();

            var invitedRole = RoleEntity
                    .builder()
                    .roleEnum(RoleEnum.INVITED)
                    .permissionSet(Set.of(readPermission))
                    .build();

            var developerRole = RoleEntity
                    .builder()
                    .roleEnum(RoleEnum.DEV)
                    .permissionSet(Set.of(createPermission,
                            readPermission,
                            updatePermission,
                            deletePermission))
                    .build();

            // Creating the users
            var isaiasUser = UserEntity
                    .builder()
                    .username("isaias")
                    .password(new BCryptPasswordEncoder().encode("1234"))
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialsNoExpired(true)
                    .roles(Set.of(adminRole, userRole))
                    .build();

            var yamilaUser = UserEntity
                    .builder()
                    .username("yamila")
                    .password(new BCryptPasswordEncoder().encode("12345"))
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialsNoExpired(true)
                    .roles(Set.of(userRole))
                    .build();

            userEntityRepository.saveAll(Set.of(isaiasUser, yamilaUser));
        };
    }
}
