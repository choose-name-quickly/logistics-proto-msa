package com.namequickly.logistics.slack_message.domain.repository;

import com.namequickly.logistics.slack_message.domain.model.SlackMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SlackMessageRepo extends JpaRepository<SlackMessage, UUID>, SlackMessageRepoCustom {

}
