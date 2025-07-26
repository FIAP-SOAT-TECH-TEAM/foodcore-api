import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';

export let check_failure_rate = new Rate('check_failure_rate');

export let options = {
  stages: [
    { duration: '6m', target: 100 },
    { duration: '30s', target: 0 },
  ],
  thresholds: {
    check_failure_rate: ['rate<0.01'],
    http_req_duration: ['p(95)<1000'],
  },
};

export default function () {
  const payload = JSON.stringify({
    email: "ddosattack@email.com",
    password: "passwdverystrong123"
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const res = http.post('http://foodcoreapi2.eastus.cloudapp.azure.com/api/users/login', payload, params);

  const success = check(res, {
    'StatusCode é 404': (r) => r.status === 404,
  });

  // Registra falha se o check falhar
  check_failure_rate.add(!success);

  sleep(1);
}