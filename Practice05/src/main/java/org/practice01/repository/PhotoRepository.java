package org.practice01.repository;

import org.practice01.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
//    List<Photo> findByLibrary_lid(Integer libraryId);

    List<Photo> findByEmployee_EidAndDateBetween(Integer eid, LocalDate fromDate, LocalDate toDate);
    @Query("SELECT p FROM Photo p WHERE p.employee.eid = :eid")
    List<Photo> findPhotosByEmployeeId(@Param("eid") Integer employeeId);
}
