import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        coupon_stampede: {
            executor: 'shared-iterations',
            vus: 100,           // 100명의 가상유저 가
            iterations: 100,    // 총 100번의 발급 요청을 동시에 수행
            maxDuration: '10s',
        },
    },
};

export default function () {
    const url = 'http://localhost:8080/api/v1/coupons/1/issue';
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(url, null, params);

    check(res, {
        '정상 처리 (200 OK 또는 400 OutOfStock)': (r) => r.status === 200 || r.status === 400,
        '500 서버 장애 없음': (r) => r.status !== 500,
    });
}