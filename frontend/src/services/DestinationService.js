import axios from 'axios';

const DESTINATION_API_BASE_URL = '/destinations';

const PUBLIC_DESTINATION_API_BASE_URL = '/publicDestinations';
class DestinationService {

    getDestinations() {
        return axios.get(DESTINATION_API_BASE_URL);
    }

    getUrl() {
        return DESTINATION_API_BASE_URL;
    }

    getPublicUrl() {
        return PUBLIC_DESTINATION_API_BASE_URL;
    }
}

export default new DestinationService();