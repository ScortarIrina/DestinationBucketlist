import axios from 'axios';

const DESTINATION_API_BASE_URL = '/privateDestinations';

class PrivateDestinationService {

    getDestinations() {
        return axios.get(DESTINATION_API_BASE_URL);
    }

    getUrl() {
        return DESTINATION_API_BASE_URL;
    }
}

export default new PrivateDestinationService();