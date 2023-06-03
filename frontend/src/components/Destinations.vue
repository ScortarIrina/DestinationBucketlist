<template>
  <div class="container" id="container">
    <Loader :loading="showLoader"/>
    <TabsWrapper>
      <TabItem title="PrivateDestinations">
        <table class="table table-striped" id="contentTableDogs">
          <table class="table table-striped" id="contentTableDogsList">
            <thead>
            <tr>
              <input type="button" value="Update private destination" class="btn btn-primary"
                     @click="showModalClicked(this.clickedDog, false, true, false, false)"/>
              <input type="button" value="Create private destination" class="btn btn-primary"
                     @click="showModalClicked(this.clickedDog, false, false, true, false)"/>
              <input type="button" value="Delete private destination" class="btn btn-primary"
                     @click="showModalClicked(this.clickedDog, false, false, false, true)"/>
            </tr>
            <tr>
              <th>
                <h2 class="text-center"> Private Destinations List &#128203;</h2>
              </th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>
                <div class="container">
                  <span class="showItems">
                    Show Items:
                    <select @change="onChangeRecordsPerPage" v-model.number="recordsPerPage">
                      <option :value="10">10</option>
                      <option :value="20">20</option>
                      <option :value="30">30</option>
                      <option :value="40">40</option>
                      <option :value="50">50</option>
                      <option :value="100">100</option>
                    </select>
                  </span>
                  <span>
                    Go to Page <input type="text" v-model="enterPageNo"><button type="button"
                                                                                @click.prevent="gotoPage">Go</button>
                  </span>
                  <span>
                    <Pagination v-if="sortedPrivateDestinations" :total-pages='this.totalPages'
                                                :per-page='this.recordsPerPage' :current-page='this.page'
                                                @pagechanged="onPageChange"/>
                  </span>
                  <table class="table table-striped" id="privateDestinationsTable">
                    <thead>
                    <tr>
                      <th @click="sortList('id')"><h5 class="text-center"> Id<span
                          id="sid"
                          style="opacity: 0"> ▲</span>
                      </h5></th>
                      <th @click="sortList('geolocation')"><h5 class="text-center"> Geolocation<span
                          id="sgeolocation"
                          style="opacity: 0"> ▲</span>
                      </h5></th>
                      <th @click="sortList('title')"><h5 class="text-center"> Title<span
                          id="stitle"
                          style="opacity: 0"> ▲</span></h5></th>
                      <th @click="sortList('image')"><h5 class="text-center"> Image<span
                          id="simage"
                          style="opacity: 0"> ▲</span>
                      </h5></th>
                      <th @click="sortList('description')"><h5 class="text-center"> Description<span
                          id="sdescription"
                          style="opacity: 0"> ▲</span></h5></th>
                      <th @click="sortList('startDate')"><h5 class="text-center"> Start Date<span
                          id="sstartDate"
                          style="opacity: 0"> ▲</span></h5></th>
                      <th @click="sortList('endDate')"><h5 class="text-center"> End Date<span
                          id="sendDate"
                          style="opacity: 0"> ▲</span></h5></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="privateDestination in sortedPrivateDestinations" v-bind:key="privateDestination.id"
                        @click="showModalClicked(privateDestination, false, true, false, false)">
                      <td> {{ privateDestination.id }}</td>
                      <td> {{ privateDestination.geolocation }}</td>
                      <td> {{ privateDestination.title }}</td>
                      <td> {{ privateDestination.image }}</td>
                      <td> {{ privateDestination.description }}</td>
                      <td> {{ privateDestination.startDate }}</td>
                      <td> {{ privateDestination.endDate }}</td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </table>
      </TabItem>

      <TabItem title="Public Destinations">Owners</TabItem>

    </TabsWrapper>

    <Modal ref="modal" v-show="showModal" @close-modal="updateAndCloseModal()" :div-height="this.divHeight"
           @keydown.esc="updateAndCloseModal()" tabindex="0" id="modal">

      <div v-show="this.updatePrivateDestinationClicked" class="add-form">
        <h6>DESTINATION DETAILS</h6>
        <form v-on:submit.prevent="onSubmitUpdate" class="add-form">

          <div class="form-control">
            <label>ID: </label>
            <span v-show="this.clickedPrivateDestination.id !== ''" id="update_span_id">{{ this.clickedPrivateDestination.id }}</span>
            <input type="text" v-show="this.clickedPrivateDestination.id === ''" id="update_input_id"
                   v-bind="this.clickedPrivateDestination.id"
                   placeholder="Enter private destination ID"/>
          </div>

          <div class="form-control">
            <label>Geolocation: </label>
            <input type="text" v-model="clickedPrivateDestination.geolocation" id="geolocation"
                   placeholder="Enter destination geolocation"/>
          </div>

          <div class="form-control">
            <label>Title: </label>
            <input type="text" v-model="clickedPrivateDestination.title" id="title"
                   placeholder="Enter destination title"/>
          </div>

          <div class="form-control">
            <label>Image: </label>
            <input type="text" v-model="clickedPrivateDestination.image" id="image"
                   placeholder="Enter destination image link"/>
          </div>

          <div class="form-control">
            <label>Description: </label>
            <input type="text" v-model="clickedPrivateDestination.description" id="description"
                   placeholder="Enter destination description"/>
          </div>

          <div class="form-control">
            <label>Start Date: </label>
            <input type="text" v-model="clickedPrivateDestination.startDate" id="startDate"
                   placeholder="Enter destination start date"/>
          </div>

          <div class="form-control">
            <label>End Date: </label>
            <input type="text" v-model="clickedPrivateDestination.endDate" id="endDate"
                   placeholder="Enter destination end date"/>
          </div>

          <hr>
          <input type="submit" value="Update destination" class="btn btn-primary"/>
        </form>
      </div>

      <div v-show="this.createPrivateDestinationClicked" class="add-form">
        <h6>DESTINATION DETAILS</h6>
        <form v-on:submit.prevent="onSubmitCreate" class="add-form">

          <div class="form-control">
            <label>Geolocation: </label>
            <input type="text" v-model="createForm.geolocation" id="geolocation"
                   placeholder="Enter destination geolocation"/>
          </div>

          <div class="form-control">
            <label>Title: </label>
            <input type="text" v-model="createForm.title" id="title"
                   placeholder="Enter destination title"/>
          </div>

          <div class="form-control">
            <label>Image: </label>
            <input type="text" v-model="createForm.image" id="image"
                   placeholder="Enter destination image link"/>
          </div>

          <div class="form-control">
            <label>Description: </label>
            <input type="text" v-model="createForm.description" id="description"
                   placeholder="Enter destination description"/>
          </div>

          <div class="form-control">
            <label>Start Date: </label>
            <input type="text" v-model="createForm.startDate" id="startDate"
                   placeholder="Enter destination start date"/>
          </div>

          <div class="form-control">
            <label>End Date: </label>
            <input type="text" v-model="createForm.endDate" id="endDate"
                   placeholder="Enter destination end date"/>
          </div>

          <hr>
          <input type="submit" value="Add destination" class="btn btn-primary"/>

        </form>
      </div>

      <div v-show="this.deletePrivateDestinationClicked" class="add-form">
        <form v-on:submit.prevent="onSubmitDelete" class="add-form">
          <div class="form-control">
            <label>ID: </label>
            <input type="text" v-model="deleteForm.id" id="id"
                   placeholder="Enter destination ID"/>
          </div>
          <hr>
          <input type="submit" value="Delete destination" class="btn btn-primary"/>
        </form>
      </div>
      <div v-show="this.listPrivateDestinationsClicked" class="add-form">
        <form v-on:submit.prevent="onSubmitShowFiltered" class="add-form">
          <div class="form-control">
            <span>Filter: </span>
            <input type="text" v-model="filterForm.startDate" id="startDate"
                   placeholder=" "/>
            <input type="submit" value="Filter private destinations" class="btn btn-primary"/>
          </div>
        </form>
      </div>
    </Modal>

  </div>
</template>

<script>

import axios from 'axios';
import Loader from './Loader.vue'
import Pagination from './Pagination.vue'
import TabsWrapper from './TabsWrapper.vue'
import TabItem from './TabItem.vue'
import Modal from "@/components/Modal.vue";
import PrivateDestinationService from "../services/PrivateDestinationService";

export default {
  name: 'Destinations',
  components: {
    Modal,
    Loader,
    Pagination,
    TabsWrapper,
    TabItem
  },

  data() {
    return {
      divHeight: 440,
      showModal: false,

      listPrivateDestinationsClicked: false,
      updatePrivateDestinationClicked: false,
      createPrivateDestinationClicked: false,
      deletePrivateDestinationClicked: false,

      emptyPrivateDestination: {
        id: '',
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      },

      clickedPrivateDestination: {
        id: '',
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      },

      privateDestinations: [],

      createForm: {
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      },

      deleteForm: {
        id: ''
      },

      updateForm: {
        id: '',
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      },

      filterForm: {
        startDate: ''
      },

      currentSort: 'id',
      currentSortDir: 'desc',
      showLoader: false,
      page: 1,
      totalPages: 0,
      totalRecords: 0,
      recordsPerPage: 10,
      enterPageNo: '',
    };
  },

  mounted() {
    this.sortList('id');
  },

  computed: {
    sortedPrivateDestinations: function () {
      return this.privateDestinations.sort((a, b) => {
        let modifier = 1;
        if (this.currentSortDir === 'desc') modifier = -1;
        if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
        if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
        return 0;
      });
    }
  },

  methods: {
    showModalClicked(privateDestination, lClicked, uClicked, cClicked, dClicked) {
      this.clickedPrivateDestination = privateDestination;
      this.listPrivateDestinationsClicked = lClicked;
      this.updatePrivateDestinationClicked = uClicked;
      this.createPrivateDestinationClicked = cClicked;
      this.deletePrivateDestinationClicked = dClicked;
      this.showModal = true;
      if (this.listPrivateDestinationsClicked) {
        this.divHeight = 150;
      } else if (this.deletePrivateDestinationClicked) {
        this.divHeight = 200;
      } else {
        this.divHeight = 440;
      }
    },

    updateAndCloseModal() {
      this.showModal = false
      document.getElementById('update_input_id').value = '';
      this.clickedPrivateDestination = this.emptyPrivateDestination;
    },

    getPrivateDestinations() {
      PrivateDestinationService.getDestinations().then((response) => {
        this.privateDestinations = response.data.privateDestinations;
        this.totalRecords = response.data.totalItems;
        this.totalPages = response.data.totalPages;
        this.page = response.data.currentPage + 1;
      });
    },

    onSubmitCreate(e) {
      e.preventDefault()
      if (!this.createForm.title) {
        alert('Please Add a Title')
        return
      }

      this.showLoader = true
      axios.post(PrivateDestinationService.getUrl(), this.createForm)
          .then((res) => {
            window.location.reload()
          })
          .catch((error) => {
            alert(JSON.stringify(error.response.data));
            console.log(JSON.stringify(error.response.data));
          }).finally(() => {
        this.showLoader = false
      });
      this.createForm.geolocation = ' '
      this.createForm.image = ' '
      this.createForm.title = ' '
      this.createForm.description = ' '
      this.createForm.startDate = ''
      this.createForm.endDate = ''
    },

    onSubmitDelete(e) {
      e.preventDefault()
      if (!this.deleteForm.id) {
        alert('Please Add an ID')
        return
      }

      this.showLoader = true
      axios.delete(PrivateDestinationService.getUrl() + '/' + this.deleteForm.id, this.deleteForm)
          .then((res) => {
            window.location.reload()
          })
          .catch((error) => {
            alert(JSON.stringify(error.response.data));
            console.log(JSON.stringify(error.response.data));
          }).finally(() => {
        this.showLoader = false
      });
      this.deleteForm.id = ' '
    },

    onSubmitUpdate(e) {
      e.preventDefault()

      // use local const, we don't want changes to affect for due to reactivity
      const localPrivateDestination = {
        id: '',
        title: '',
        image: '',
        geolocation: '',
        description: '',
        startDate: '',
        endDate: ''
      }

      localPrivateDestination.id = this.clickedPrivateDestination.id;
      localPrivateDestination.title = this.clickedPrivateDestination.title;
      localPrivateDestination.image = this.clickedPrivateDestination.image;
      localPrivateDestination.geolocation = this.clickedPrivateDestination.geolocation;
      localPrivateDestination.description = this.clickedPrivateDestination.description;
      localPrivateDestination.startDate = this.clickedPrivateDestination.startDate;
      localPrivateDestination.endDate = this.clickedPrivateDestination.endDate;

      if (!localPrivateDestination.id) {
        localPrivateDestination.id = document.getElementById('update_input_id').value;
      }

      if (!localPrivateDestination.id) {
        localPrivateDestination.id = document.getElementById('update_span_id').textContent;
      }

      if (!localPrivateDestination.id) {
        alert('Please Add an ID');

        this.clickedPrivateDestination = this.emptyPrivateDestination;

        window.location.reload();

        return;
      }
      if (!localPrivateDestination.title) {
        alert('Please Add a title');
        this.clickedPrivateDestination = this.emptyPrivateDestination;

        window.location.reload();

        return;
      }

      this.showLoader = true;
      axios.put(PrivateDestinationService.getUrl() + '/' + localPrivateDestination.id, localPrivateDestination)
          .then((res) => {
            window.location.reload();
          })
          .catch((error) => {
            alert(JSON.stringify(error.response.data));
            console.log(JSON.stringify(error.response.data));
            window.location.reload();
          }).finally(() => {
        this.clickedPrivateDestination = this.emptyPrivateDestination;
        this.showLoader = false;
      });
    },

    onSubmitShowFiltered(e) {
      e.preventDefault()
      if (this.filterForm.weight.length === 0) {
        this.loadPage();
      } else {
        this.showLoader = true
        axios.get(PrivateDestinationService.getUrl() + '/privateDestinations' + this.filterForm.weight)
            .then((response) => {
              this.dogs = response.data;
            }).finally(() => {
          this.showLoader = false;
        });
      }
    },

    loadPage() {
      this.showLoader = true
      const updatedPage = this.page - 1;
      this.showLoader = true;
      axios.get(PrivateDestinationService.getUrl() + '?page=' + updatedPage + '&size=' + this.recordsPerPage)
          .then((response) => {
            this.showLoader = false
            this.privateDestinations = response.data.privateDestinations;
            this.totalRecords = response.data.totalItems;
            this.totalPages = response.data.totalPages;
            this.page = response.data.currentPage + 1;
          }).finally(() => {
        this.showLoader = false;
      });
    },

    onPageChange(page) {
      this.page = page
      this.loadPage()
    },
    onChangeRecordsPerPage() {
      this.loadPage()
    },
    gotoPage() {
      if (!isNaN(parseInt(this.enterPageNo))) {
        this.page = parseInt(this.enterPageNo)
        this.loadPage()
      }
    },

    sortList: function (s) {
      // remove icon of current sort icon
      document.getElementById('s' + this.currentSort).style.opacity = "0";
      //if s == current sort, reverse
      if (s === this.currentSort) {
        this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';
      } else {
        this.currentSortDir = 'asc';
      }
      this.currentSort = s;
      document.getElementById('s' + this.currentSort).innerHTML = this.currentSortDir === 'asc' ? ' ▲' : ' ▼';
      document.getElementById('s' + this.currentSort).style.opacity = "1";
    },

    updateUpdateForm: function (privateDestination) {
      this.updateForm.id = privateDestination.id;
      this.updateForm.title = privateDestination.title;
      this.updateForm.geolocation = privateDestination.geolocation;
      this.updateForm.description = privateDestination.description;
      this.updateForm.image = privateDestination.image;
      this.updateForm.startDate = privateDestination.startDate;
      this.updateForm.endDate = privateDestination.endDate;
    }

  },
  created() {
    this.loadPage();
    this.escapeHandler = (e) => {
      if (e.key === 'Escape' && this.showModal) {
        this.updateAndCloseModal();
      }
    }

    document.addEventListener('keydown', this.escapeHandler);
  },
  unmounted() {
    document.removeEventListener('keydown', this.escapeHandler);
  }
}

</script>

<style>
#privateDestinationsTable {
  width: max-content;
  table-layout: auto;
}

#container {
  position: relative;
}

h5:hover {
  cursor: pointer;
  background: rgb(229, 255, 211);
}

.showItems {
  display: inline-block;
  margin-left: -35px;

  li {
    list-style-type: none;
    display: inline-block;
    margin-left: 10px;
  }
}
</style>