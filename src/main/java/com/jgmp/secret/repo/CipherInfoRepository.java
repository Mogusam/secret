package com.jgmp.secret.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CipherInfoRepository extends CrudRepository<CipherInfo, Integer> {


}
