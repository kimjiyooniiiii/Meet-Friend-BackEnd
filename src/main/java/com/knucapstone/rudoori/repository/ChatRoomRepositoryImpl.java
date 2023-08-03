package com.knucapstone.rudoori.repository;

import com.knucapstone.rudoori.model.entity.ChatRoom;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{

    private final MongoOperations mongoOperations;

    public ChatRoomRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<ChatRoom> searchByKeyword(String[] keywords) {
        Criteria criteria = new Criteria();
        List<Criteria> list = new ArrayList<>();

        for(String k : keywords) {
            list.add(
            new Criteria().orOperator(
                    Criteria.where("roomName").regex(k,"i"),
                    Criteria.where("introduce").regex(k,"i")
            )
            );
        }

        criteria.orOperator(list.toArray(new Criteria[0]));

        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

        return mongoOperations.find(query, ChatRoom.class);
    }
}
