CREATE TABLE `writer` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'pk',
                          `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '이름',
                          `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '이메일',
                          `reg_dt` datetime NOT NULL COMMENT '등록일',
                          `mod_dt` datetime NOT NULL COMMENT '수정일',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `writer_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `schedule` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'PK',
                            `schedule` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '할 일',
                            `reg_dt` datetime NOT NULL COMMENT '작성일',
                            `mod_dt` datetime NOT NULL COMMENT '수정일',
                            `del_dt` datetime DEFAULT NULL COMMENT '삭제일',
                            `writer_id` bigint NOT NULL COMMENT '작성자 FK',
                            `password` varchar(100) NOT NULL COMMENT '비밀번호',
                            PRIMARY KEY (`id`),
                            KEY `schedule_writer_FK` (`writer_id`),
                            CONSTRAINT `schedule_writer_FK` FOREIGN KEY (`writer_id`) REFERENCES `writer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;