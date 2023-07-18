package com.farmer.backend.domain.search;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search,Long> {

    String findBySearchWord(String searchWord);

}
