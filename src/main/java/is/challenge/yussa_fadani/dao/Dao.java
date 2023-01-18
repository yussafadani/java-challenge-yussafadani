package is.challenge.yussa_fadani.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import is.challenge.yussa_fadani.entity.UserEntity;

public interface Dao extends JpaRepository<UserEntity, Long>{
	
//	@Modifying(clearAutomatically = true)
//	@Query("update user_login set username = :username , password = :password where username = :username2")
//	int updateUser(@Param("username") int username, @Param("password") String password,@Param("username2") int username2 );
}
