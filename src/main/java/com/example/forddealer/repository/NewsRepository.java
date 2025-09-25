package com.example.forddealer.repository;

import com.example.forddealer.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
  
}
