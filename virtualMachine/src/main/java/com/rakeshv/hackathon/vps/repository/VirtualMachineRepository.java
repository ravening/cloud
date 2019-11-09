package com.rakeshv.hackathon.vps.repository;
import com.rakeshv.hackathon.vps.domain.VirtualMachine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VirtualMachine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {

}
