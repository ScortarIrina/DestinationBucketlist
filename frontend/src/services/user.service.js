import axios from 'axios';
import authHeader from '@/services/auth-header';

const API_URL = '/users';

class UserService {
    getUrl() {
        return API_URL;
    }
}

export default new UserService();
