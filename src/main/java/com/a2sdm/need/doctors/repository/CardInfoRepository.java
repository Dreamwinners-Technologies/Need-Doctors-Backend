package com.a2sdm.need.doctors.repository;

import com.a2sdm.need.doctors.jwt.model.UserModel;
import com.a2sdm.need.doctors.model.CardModel;
//import org.kolobok.annotation.FindWithOptionalParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardInfoRepository extends JpaRepository<CardModel, String> {


    //    @FindWithOptionalParams
    Optional<List<CardModel>> findByNameAndSpecializationAndThanaAndDistrict(String name, String specialization, String thana, String district);


//    @Query("SELECT * FROM card_info WHERE (:name is null or name = :name) " +
//            "and (:specialization is null or specialization = :specialization) " +
//            "and (:district is null or district = :district)" +
//            "(:thana is null or thana = :thana)")
//    List<CardModel> getData(@Param("name") String name,@Param("specialization") String specialization,
//                            @Param("thana") String thana,@Param("district") String district);


    @Query(value = "SELECT * FROM card_info WHERE ((card_info.name = :name) or (card_info.name = null and :name = null)) " +
        "and (:specialization is null or card_info.specialization = :specialization) ", nativeQuery = true)
    List<CardModel> getData(@Param("name") String name, @Param("specialization") String specialization);

}
