package is.challenge.yussa_fadani.service;

import java.util.List;
import java.util.Map;

import is.challenge.yussa_fadani.entity.UserEntity;

public interface ServiceInterface {
	public List<UserEntity> testGetApi();
	public List<UserEntity> ListUser() throws Exception;
	public Map<String, Object> Registrasi(UserEntity entity) throws Exception;
	public Map<String, Object> login(UserEntity entity) throws Exception;
	public Map<String, Object> editUser(UserEntity entity) throws Exception;
	public UserEntity findById(Long id);
}
