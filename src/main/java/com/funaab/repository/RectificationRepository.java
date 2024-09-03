package com.funaab.repository;

import com.funaab.model.Admin;
import com.funaab.model.Rectification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RectificationRepository extends JpaRepository<Rectification,Long> {

}
