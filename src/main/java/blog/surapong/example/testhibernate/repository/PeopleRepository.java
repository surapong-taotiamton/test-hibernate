package blog.surapong.example.testhibernate.repository;

import blog.surapong.example.testhibernate.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Long> {
}
