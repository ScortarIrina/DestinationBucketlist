import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
    login(user) {
        return axios
            .post(API_URL + 'login', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'register', {
            username: user.username,
            password: user.password,
            profile: user.profile,
            firstName: user.firstName,
            lastName: user.lastName,
            location: user.location,
            birthday: user.birthday,
            gender: user.gender,
            maritalStatus: user.maritalStatus
        });
    }
}

export default new AuthService();
