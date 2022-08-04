package com.fastcampus.ch3.TraComRol;

public class TransactionCommitRollback {
    /*
    Transaction이란?
    더이상 나눌수 없는 작업의 단위(Tx)
    insert, update, select 하나하나가 다 TX.
    계좌이체의 경우, 출금(update)과 입금(update)이 하나의 Tx로 묶여야 됨.
    (출금이 성공하고 입금이 실패할 경우 제대로 된 작업이 아니므로 RollBack 되어야 한다)
    '모'아니면 '도'. 출금과 입금이 둘 다 성공하지 않으면 실패.
    하나만 실패해도 취소돼야 함.(all or nothing)

   Transaction의 속성 - ACID
    원자성 (Atomicity) - 나눌 수 없는 하나의 작업으로 다뤄져야 한다 (계좌이체 = 출금+입금)
    일관성 (Consistency) - Tx 수행 전과 후가 일관된 상태를 유지해야 한다 ( A: 500-200 ---- 200 ---> B: 300+200 )
    고립성 (Isolation) - 각 Tx는 독립적으로 수행되어야 한다 ( Tx1 , Tx2 서로 영향을 주지 말아야한다)
    영속성 (Durability) - 성공한 Tx의 결과는 유지되어야 한다

    Tx의 Isolation Level
    각 Tx을 고립시키는 정도
    READ UNCOMMITED - 커밋되지 않은 데이터도 읽기 가능(dirty read)
    (Tx1이 select작업 실행. Tx2이 insert 후 commit하지 않아도 Tx1에서 보임)

    READ COMMITED - 커밋된 데이터만 읽기 가능(phantom read)
    (Tx1이 select작업 실행. Tx2이 insert 후 commit하지않으면 Tx1에서 안보임)

    REPEATABLE READ(default) - Tx이 시작된 이후 변경은 무시됨
    (Tx1이 select작업 실행. Tx2가 insert 후 commit해도, Tx1이 commit하지 않으면 안보임)

    SERIALIZABLE - 한번에 하나의 Tx만 독립적으로 수행(고립도 ↑, 직렬화가 될 수록 성능은 떨어지고, 품질은 높아짐)
    (Tx1이 select작업 실행. Tx2가 insert하면 Running..뜨면서 안됨. 이 때 Tx1이 commit하면 Tx2에 insert가능)

     */

    /*
    commit, rollback
    커밋(commit) - 작업 내용을 DB에 영구적으로 저장
    롤백(rollback) - 최근 변경사항을 취소(마지막 커밋으로 복귀)

    자동커밋(Auto commit), 수동커밋
    Auto commit이 기본
    자동커밋 - 명령 실행 후, 자동으로 커밋이 수행(rollback 불가)
    수동커밋 - 명령 실행 후, 명시적으로 commit또는 rollback을 입력

    */
}
