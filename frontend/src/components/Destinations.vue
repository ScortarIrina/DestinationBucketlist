<template>
  <div class="container" id="container">
    <Loader :loading="showLoader"/>
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <div class="navbar-nav mr-auto">
        <li v-if="userAuthenticated" class="nav-item">
          <a class="nav-link" @click.prevent="logOut">LogOut</a>
        </li>
        <li v-if="!userAuthenticated" class="nav-item">
          <a class="nav-link" @click.prevent="this.showModalLogin=true">Login</a>
        </li>
        <li v-if="!userAuthenticated" class="nav-item">
          <a class="nav-link" @click.prevent="this.showModalRegister=true">Register</a>
        </li>
      </div>
      <div class="navbar-nav ms-auto">
        <li v-if="userAuthenticated" class="nav-item">
          <a class="nav-link" @click="onClickedUser(currentUser.username)">
            <span class="nav-link">{{ currentUser.username }}: {{ currentUser.profiles[0] }}</span>
          </a>

        </li>
      </div>
    </nav>

    <TabsWrapper @tabClicked="tabClicked" class="allTabs">

      <TabItem title="Destinations" class="tabs">
        <table class="table table-striped" id="contentTableDestinations">
          <table class="table table-striped" id="contentTablePrivatDestinationsList">
            <thead>
            <tr>
              <input type="button" value="Update destination" class="btn btn-primary"
                     @click="showModalClickedDestinations(this.clickedDestination, false, true, false, false)"/>
              <input type="button" value="Create destination" class="btn btn-primary"
                     @click="showModalClickedDestinations(this.clickedDestination, false, false, true, false)"/>
              <input type="button" value="Delete destination" class="btn btn-primary"
                     @click="showModalClickedDestinations(this.clickedDestination, false, false, false, true)"/>
            </tr>
            <tr>
              <th>
                <h2 class="text-center"> Destinations List &#128203;</h2>
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
                    Go to Page <input type="text" v-model="enterPageNo">
                    <button type="button" @click.prevent="gotoPageDestinations">Go</button>
                  </span>
                  <span>
                    <Pagination v-if="sortedDestinations"
                                :total-pages='this.totalPages'
                                :per-page='this.recordsPerPage'
                                :current-page='this.page'
                                @pagechanged="onPageChangeDestinations"/>
                  </span>
                  <table class="table table-striped" id="destinationsTable">
                    <thead>
                    <tr>
                      <th @click="sortList('id')">
                        <h5 class="text-center"> Id
                          <span
                              id="sid"
                              style="opacity: 0"> ▲
                          </span>
                        </h5>
                      </th>
                      <th @click="sortList('geolocation')">
                        <h5 class="text-center"> Geolocation
                          <span
                              id="sgeolocation"
                              style="opacity: 0"> ▲
                          </span>
                        </h5>
                      </th>
                      <th @click="sortList('title')">
                        <h5 class="text-center"> Title
                          <span
                              id="stitle"
                              style="opacity: 0"> ▲
                          </span>
                        </h5>
                      </th>
                      <th @click="sortList('image')">
                        <h5 class="text-center"> Image
                          <span
                              id="simage"
                              style="opacity: 0"> ▲
                          </span>
                        </h5>
                      </th>
                      <th @click="sortList('description')">
                        <h5 class="text-center"> Description
                          <span
                              id="sdescription"
                              style="opacity: 0"> ▲
                          </span>
                        </h5>
                      </th>
                      <th @click="sortList('startdate')">
                        <h5 class="text-center"> Start date
                          <span
                              id="sstartdate"
                              style="opacity: 0"> ▲
                          </span>
                        </h5>
                      </th>
                      <th @click="sortList('enddate')">
                        <h5 class="text-center"> End date
                          <span
                              id="senddate"
                              style="opacity: 0"> ▲
                          </span>
                        </h5>
                      </th>
                      <th >
                        <h5 class="text-center"> Public
                          <span
                              id="public"
                              style="opacity: 0"> ▲
                          </span>
                        </h5>
                      </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr :class="d.destClassName" v-for="d in sortedDestinations" v-bind:key="d.item.id"
                        @click="showModalClickedDestinations(d.item, false, true, false, false)">
                      <td> {{ d.item.id }}</td>
                      <td> {{ d.item.geolocation }}</td>
                      <td> {{ d.item.title }}</td>
                      <td> {{ d.item.image }}</td>
                      <td> {{ d.item.description }}</td>
                      <td> {{ d.item.startDate }}</td>
                      <td> {{ d.item.endDate }}</td>
                      <td @click.stop="togglePublished(d.item)"><SvgIcon :name="d.svgName" /></td>
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

    </TabsWrapper>

    <Modal ref="modal" v-show="this.showModalLogin&&!this.userAuthenticated"
           @close-modal="this.showModalLogin = false" @keydown.esc="this.showModalLogin = false"
           :div-height="this.divHeight" tabindex="0" id="modal">
      <div v-show="!this.userAuthenticated" class="add-form">
        <h6>USER LOGIN</h6>

        <Form @submit="onSubmitLogin" :validation-schema="schemaLogin" class="add-form">
          <div class="form-control">
            <label for="username">Username</label>
            <Field name="username" type="text" class="form-control" v-model="this.user.username"
                   placeholder="Enter username"/>
            <ErrorMessage name="username" class="error-feedback"/>
          </div>
          <div class="form-control">
            <label for="password">Password</label>
            <Field name="password" type="password" class="form-control" v-model="this.user.password"
                   placeholder="Enter password"/>
            <ErrorMessage name="password" class="error-feedback"/>
          </div>

          <div class="form-control">
            <input type="submit" value="Login" class="btn btn-primary"/>
          </div>

          <div v-if="message" class="form-control">
            <div v-if="message" class="alert alert-danger" role="alert">
              {{ message }}
            </div>
          </div>
        </Form>
      </div>
    </Modal>

    <Modal ref="modal" v-show="this.showModalRegister&&!this.userAuthenticated"
           @close-modal="this.showModalRegister = false" @keydown.esc="this.showModalRegister = false"
           :div-height="this.divHeight" tabindex="0" id="modal">
      <div v-show="!this.userAuthenticated" class="add-form">
        <h6>USER REGISTER</h6>

        <Form @submit="onSubmitRegister" :validation-schema="schemaReg" class="add-form">
          <div class="form-control">
            <label for="username">Username</label>
            <Field name="username" type="text" class="form-control" v-model="this.user.username"
                   placeholder="Enter username"/>
            <ErrorMessage name="username" class="error-feedback"/>
            <br>
            <label for="password">Password</label>
            <Field name="password" type="password" class="form-control" v-model="this.user.password"
                   placeholder="Enter password"/>
            <ErrorMessage name="password" class="error-feedback"/>
            <br>
            <label for="profile">Profile</label>
            <Field name="profile" as="select" class="form-control" v-model="this.user.profile">
              <option value="ADMIN" selected>ADMIN</option>
              <option value="USER">REGULAR</option>
            </Field>
            <ErrorMessage name="profile" class="error-feedback"/>
            <br>
            <label for="firstName">First name</label>
            <Field name="firstName" type="text" class="form-control" v-model="this.user.firstName"
                   placeholder="Enter first name"/>
            <ErrorMessage name="firstName" class="error-feedback"/>
            <br>
            <label for="lastName">Last name</label>
            <Field name="lastName" type="text" class="form-control" v-model="this.user.lastName"
                   placeholder="Enter last name"/>
            <ErrorMessage name="lastName" class="error-feedback"/>
            <br>
            <label for="location">Location</label>
            <Field name="location" type="text" class="form-control" v-model="this.user.location"
                   placeholder="Enter location"/>
            <ErrorMessage name="location" class="error-feedback"/>
            <br>
            <label for="birthday">Birthday</label>
            <Field name="birthday" type="text" class="form-control" v-model="this.user.birthday"
                   placeholder="Enter birthday"/>
            <ErrorMessage name="birthday" class="error-feedback"/>
            <br>
            <label for="gender">Gender</label>
            <Field name="gender" as="select" class="form-control" v-model="this.user.gender">
              <option value="Male" selected>MALE</option>
              <option value="Female">FEMALE</option>
            </Field>
            <ErrorMessage name="gender" class="error-feedback"/>
            <br>
            <label for="maritalStatus">Marital status</label>
            <Field name="maritalStatus" as="select" class="form-control" v-model="this.user.maritalStatus">
              <option value="MARRIED" selected>MARRIED</option>
              <option value="SINGLE">SINGLE</option>
            </Field>
            <ErrorMessage name="maritalStatus" class="error-feedback"/>
          </div>

          <div class="form-control">
            <input type="submit" value="Register" class="btn btn-primary"/>
          </div>

          <div v-if="message" class="form-control">
            <div v-if="message" class="alert alert-danger" role="alert">
              {{ message }}
            </div>
          </div>
        </Form>
      </div>
    </Modal>

    <Modal ref="modal" v-show="this.showModalUserDetails&&this.userAuthenticated"
           @close-modal="this.showModalUserDetails = false" @keydown.esc="this.showModalUserDetails = false"
           :div-height="this.divHeight" tabindex="0" id="modal">
      <div v-show="this.userAuthenticated" class="container text-center mt-5 mb-5">
        <table class="table table-sm table-striped">
          <thead>
          <th colspan="2"><h5 class="fw-bolder text-success">USER DETAILS</h5></th>
          </thead>
          <tbody>
          <tr>
            <th class="fw-bold text-end" scope="row">Username:&nbsp;</th>
            <td class="text-start">{{ clickedUser.username }}</td>
          </tr>
          <tr>
            <th class="fw-bold text-end" scope="row">Profile:&nbsp;</th>
            <td class="text-start">{{ clickedUser.profile }}</td>
          </tr>
          <tr>
            <th class="fw-bold text-end" scope="row">First name:&nbsp;</th>
            <td class="text-start">{{ clickedUser.firstName }}</td>
          </tr>
          <tr>
            <th class="fw-bold text-end" scope="row">Last name:&nbsp;</th>
            <td class="text-start">{{ clickedUser.lastName }}</td>
          </tr>
          <tr>
            <th class="fw-bold text-end" scope="row">Birthday:&nbsp;</th>
            <td class="text-start">{{ clickedUser.birthday }}</td>
          </tr>
          <tr>
            <th class="fw-bold text-end" scope="row">Gender:&nbsp;</th>
            <td class="text-start">{{ clickedUser.gender }}</td>
          </tr>
          <tr>
            <th class="fw-bold  text-end" scope="row">Location:&nbsp;</th>
            <td class="text-start">{{ clickedUser.location }}</td>
          </tr>
          <tr>
            <th class="fw-bold text-end" scope="row">Marital status:&nbsp;</th>
            <td class="text-start">{{ clickedUser.maritalStatus }}</td>
          </tr>
          <tr>
            <th class="fw-bold text-end" scope="row">Creation count:&nbsp;</th>
            <td class="text-start">{{ clickedUser.creationCount }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </Modal>

    <Modal ref="modal" v-show="showModalDestinations" @close-modal="updateAndCloseModalDestinations()"
           :div-height="this.divHeight"
           @keydown.esc="updateAndCloseModalDestinations()" tabindex="0" id="modal">
      <div v-show="this.updateDestinationClicked" class="add-form">
        <h6>DESTINATION DETAILS</h6>
        <form v-on:submit.prevent="onSubmitUpdateDestination" class="add-form">
          <div class="form-control">
            <label>ID: </label>
            <span v-show="this.clickedDestination.id !== ''" id="update_span_id">{{ this.clickedDestination.id }}</span>
            <input type="text" v-show="this.clickedDestination.id === ''" id="update_input_id"
                   v-bind="this.clickedDestination.id"
                   placeholder="Enter destination ID"/>
          </div>
          <div class="form-control">
            <label>Geolocation: </label>
            <input type="text" v-model="clickedDestination.geolocation" id="geolocation"
                   placeholder="Enter geolocation"/>
          </div>
          <div class="form-control">
            <label>Title: </label>
            <input type="text" v-model="clickedDestination.title" id="title"
                   placeholder="Enter title"/>
          </div>
          <div class="form-control">
            <label>Image: </label>
            <input type="text" v-model="clickedDestination.image" id="image"
                   placeholder="Enter image link"/>
          </div>
          <div class="form-control">
            <label>Description: </label>
            <input type="text" v-model="clickedDestination.description" id="description"
                   placeholder="Enter description"/>
          </div>
          <div class="form-control">
            <label>Start date: </label>
            <input type="text" v-model="clickedDestination.startDate" id="startDate"
                   placeholder="Enter start date"/>
          </div>
          <div class="form-control">
            <label>End date: </label>
            <input type="text" v-model="clickedDestination.endDate" id="startDate"
                   placeholder="Enter end date"/>
          </div>
          <hr>
          <input type="submit" value="Update destination" class="btn btn-primary"/>
        </form>
      </div>
      <div v-show="this.createDestinationClicked" class="add-form">
        <h6>DESTINATION DETAILS</h6>
        <form v-on:submit.prevent="onSubmitCreateDestination" class="add-form">
          <div class="form-control">
            <label>Geolocation: </label>
            <input type="text" v-model="createFormDestination.geolocation" id="geolocation"
                   placeholder="Enter geolocation"/>
          </div>
          <div class="form-control">
            <label>Title: </label>
            <input type="text" v-model="createFormDestination.title" id="title"
                   placeholder="Enter title"/>
          </div>
          <div class="form-control">
            <label>Image: </label>
            <input type="text" v-model="createFormDestination.image" id="image"
                   placeholder="Enter image link"/>
          </div>
          <div class="form-control">
            <label>Description: </label>
            <input type="text" v-model="createFormDestination.description" id="description"
                   placeholder="Enter description"/>
          </div>
          <div class="form-control">
            <label>Start date: </label>
            <input type="text" v-model="createFormDestination.startDate" id="startDate"
                   placeholder="Enter start date"/>
          </div>
          <div class="form-control">
            <label>End date: </label>
            <input type="text" v-model="createFormDestination.endDate" id="endDate"
                   placeholder="Enter end date"/>
          </div>
          <hr>
          <input type="submit" value="Add destination" class="btn btn-primary"/>
        </form>
      </div>
      <div v-show="this.deleteDestinationClicked" class="add-form">
        <form v-on:submit.prevent="onSubmitDeleteDestination" class="add-form">
          <div class="form-control">
            <label>ID: </label>
            <input type="text" v-model="deleteFormDestination.id" id="id"
                   placeholder="Enter destination ID"/>
          </div>
          <hr>
          <input type="submit" value="Delete Destination" class="btn btn-primary"/>
        </form>
      </div>
      <div v-show="this.listDestinationsClicked" class="add-form">
        <form v-on:submit.prevent="onSubmitShowFilteredDestinations" class="add-form">
          <div class="form-control">
            <span>Filter: </span>
            <input type="text" v-model="filterFormDestination.title" id="title"
                   placeholder=" destinations title"/>
            <input type="submit" value="Filter destinations" class="btn btn-primary"/>
          </div>
        </form>
      </div>
    </Modal>

  </div>
</template>

<script>

import DestinationService from '@/services/DestinationService';
import axios from 'axios';
import Loader from '@/components/Loader.vue'
import Pagination from '@/components/Pagination.vue'
import TabsWrapper from '@/components/TabsWrapper.vue'
import TabItem from '@/components/TabItem.vue'
import Modal from "@/components/Modal.vue";
import UserService from "@/services/user.service";
import authHeader from '@/services/auth-header';
import {Form, Field, ErrorMessage} from 'vee-validate';
import * as yup from 'yup';
import {computed} from 'vue'
import SvgIcon from "@/components/SvgIcon.vue";

export default {
  name: 'Destinations',
  components: {
    Modal,
    Loader,
    Pagination,
    TabsWrapper,
    TabItem,
    Form,
    Field,
    ErrorMessage,
    SvgIcon
  },

  data() {
    const schemaReg = yup.object().shape({
      username: yup.string().required("Username is required!"),
      password: yup.string().required("Password is required!"),
      profile: yup.string().required("Profile is required!"),
      firstName: yup.string().required("First name is required!"),
      lastName: yup.string().required("Last name is required!")
    });
    const schemaLogin = yup.object().shape({
      username: yup.string().required("Username is required!"),
      password: yup.string().required("Password is required!"),
    });
    return {
      schemaLogin,
      schemaReg,
      message: "",
      // modals
      divHeight: 440,
      showModalLogin: false,
      showModalRegister: false,
      showModalUserDetails: false,
      showModalDestinations: false,

      clickedTab: '',

      user: {
        username: '',
        password: '',
        profile: '',
        firstName: '',
        lastName: '',
        location: '',
        birthday: '',
        gender: '',
        maritalStatus: ''
      },

      // destinations
      listDestinationsClicked: false,
      updateDestinationClicked: false,
      createDestinationClicked: false,
      deleteDestinationClicked: false,

      emptyDestination: {
        id: '',
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      },
      clickedDestination: {
        id: '',
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      },
      emptyUser: {
        username: '',
        profile: '',
        firstName: '',
        lastName: '',
        location: '',
        birthday: '',
        gender: '',
        maritalStatus: '',
        creationCount: ''
      },
      clickedUser: {
        username: '',
        profile: '',
        firstName: '',
        lastName: '',
        location: '',
        birthday: '',
        gender: '',
        maritalStatus: '',
        creationCount: ''
      },
      destinations: [],
      createFormDestination: {
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      },
      deleteFormDestination: {
        id: ''
      },
      updateFormDestination: {
        id: '',
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      },
      filterFormDestination: {
        title: ''
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
    // this.sortList('id');
  },

  computed: {
    userAuthenticated() {
      return this.$store.state.auth.status.loggedIn;
    },
    currentUser() {
      return this.$store.state.auth.user;
    },
    sortedDestinations: function () {
      if (this.destinations != null) {
        const sorted = this.destinations.sort((a, b) => {
          let modifier = 1;
          if (this.currentSortDir === 'desc') modifier = -1;
          if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
          if (a[this.currentSort] > b[this.currentSort]) return modifier;
          return 0;
        });

        const newSorted = sorted.map(item => {
          let svgName = 'GreyStar';
          let destClassName = '';

          if (item.published) {
            svgName = 'YellowStar';
          }

          if (this.currentUser && item.username === this.currentUser.username) {
            destClassName = 'table-danger';
          }

          return {item, svgName, destClassName};
        });

        return newSorted;
      }
      return 1;
    },
  },

  methods: {
    UserService() {
      return UserService
    },
    logOut() {
      this.$store.dispatch('auth/logout');
      window.location.reload();
    },
    tabClicked(title) {
      this.clickedTab = title;

      this.page = 1;

      if (title === 'Destinations') {
        this.loadPageDestinations();
      }
    },

    togglePublished(destination) {
      if (this.userAuthenticated) {
        if (this.currentUser.username === destination.username) {
          // we can toggle published flag
          this.clickedDestination = destination;
          this.clickedDestination.published = !this.clickedDestination.published;
          this.onSubmitUpdateDestination();
        }
      }
    },

    showModalClickedDestinations(destination, lClicked, uClicked, cClicked, dClicked) {
      if (this.userAuthenticated) {
        if (destination.id === ""|| this.currentUser.username === destination.username) {
          this.clickedDestination = destination;
          this.listDestinationsClicked = lClicked;
          this.updateDestinationClicked = uClicked;
          this.createDestinationClicked = cClicked;
          this.deleteDestinationClicked = dClicked;
          this.showModalDestinations = true;
          if (this.listDestinationsClicked) {
            this.divHeight = 150;
          } else if (this.deleteDestinationClicked) {
            this.divHeight = 200;
          } else {
            this.divHeight = 440;
          }
        }
      }
    },

    updateAndCloseModalDestinations() {
      this.showModalDestinations = false
      document.getElementById('update_input_id').value = '';
      this.clickedDestination = this.emptyDestination;
    },

    onSubmitCreateDestination(e) {
      e.preventDefault()
      if (!this.createFormDestination.geolocation) {
        alert('Please add a GEOLOCATION for the destination')
        return
      }

      if (!this.createFormDestination.title) {
        alert('Please add a TITLE for the destination')
        return
      }

      if (!this.createFormDestination.image) {
        alert('Please add an IMAGE LINK for the destination')
        return
      }

      if (!this.createFormDestination.description) {
        alert('Please add a DESCRIPTION for the destination')
        return
      }

      this.showLoader = true
      axios.post(DestinationService.getUrl(), this.createFormDestination, {headers: authHeader()})
          .then((res) => {
            window.location.reload()
          })
          .catch((error) => {
            alert(JSON.stringify(error.response.data));
            console.log(JSON.stringify(error.response.data));
          }).finally(() => {
        this.showLoader = false
      });
      this.createFormDestination.geolocation = ' '
      this.createFormDestination.title = ' '
      this.createFormDestination.image = ' '
      this.createFormDestination.description = ' '
      this.createFormDestination.startDate = ''
      this.createFormDestination.endDate = ''
    },

    onSubmitDeleteDestination(e) {
      e.preventDefault()
      if (!this.deleteFormDestination.id) {
        alert('Please add an ID')
        return
      }

      this.showLoader = true
      axios.delete(DestinationService.getUrl() + '/' + this.deleteFormDestination.id, {headers: authHeader()})
          .then((res) => {
            window.location.reload()
          })
          .catch((error) => {
            alert(JSON.stringify(error.response.data));
            console.log(JSON.stringify(error.response.data));
          }).finally(() => {
        this.showLoader = false
      });
      this.deleteFormDestination.id = ' '
    },

    onSubmitLogin(values) {
      try {
        this.showLoader = true;

        const user = {
          username: this.user.username,
          password: this.user.password
        };

        this.$store.dispatch("auth/login", user).then(
            () => {
              this.tabClicked(this.clickedTab);
            },
            (error) => {
              console.log(error.toString())
            }
        );
      } finally {
        this.showModalLogin = false;
        this.showLoader = false;
      }
    },

    onSubmitRegister(values) {
      try {
        this.showLoader = true;

        const user = {
          username: this.user.username,
          password: this.user.password,
          profile: this.user.profile,
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          location: this.user.location,
          birthday: this.user.birthday,
          gender: this.user.gender,
          maritalStatus: this.user.maritalStatus
        };

        this.$store.dispatch("auth/register", user).then(
            () => {
              this.tabClicked(this.clickedTab);
            },
            (error) => {
              console.log(error.toString())
            }
        );
      } finally {
        this.showModalRegister = false;
        this.showLoader = false;
      }
    },

    onClickedUser(username) {
      try {
        this.clickedUser = this.emptyUser;
        this.showLoader = true;

        axios.get(UserService.getUrl() + '/username/' + username, {headers: authHeader()})
            .then(response => {
              this.clickedUser = response.data;

              return response;
            })
            .catch(err => {
              if (err.response.status === 401) {
                this.logOut();
                return null;
              } else {
                return null;
              }
            });
      } finally {
        this.showModalUserDetails = true;
        this.showLoader = false;
      }
    },


    onSubmitUpdateDestination() {
      // use local const, we don't want changes to affect for due to reactivity
      const localDestination = {
        id: '',
        geolocation: '',
        title: '',
        image: '',
        description: '',
        startDate: '',
        endDate: ''
      }

      localDestination.id = this.clickedDestination.id;
      localDestination.geolocation = this.clickedDestination.geolocation;
      localDestination.title = this.clickedDestination.title;
      localDestination.image = this.clickedDestination.image;
      localDestination.description = this.clickedDestination.description;
      localDestination.startDate = this.clickedDestination.startDate;
      localDestination.endDate = this.clickedDestination.endDate;

      if (!localDestination.id) {
        localDestination.id = document.getElementById('update_input_id').value;
      }

      if (!localDestination.id) {
        localDestination.id = document.getElementById('update_span_id').textContent;
      }

      if (!localDestination.id) {
        alert('Please add an ID for the destination');
        this.clickedDestination = this.emptyDestination;

        window.location.reload();

        return;
      }
      if (!localDestination.geolocation) {
        alert('Please add a GEOLOCATION for the destination');
        this.clickedDestination = this.emptyDestination;

        window.location.reload();

        return;
      }

      if (!localDestination.title) {
        alert('Please add a TITLE for the destination');
        this.clickedDestination = this.emptyDestination;

        window.location.reload();

        return;
      }

      if (!localDestination.image) {
        alert('Please add an IMAGE for the destination');
        this.clickedDestination = this.emptyDestination;

        window.location.reload();

        return;
      }

      if (!localDestination.description) {
        alert('Please add a DESCRIPTION for the destination');
        this.clickedDestination = this.emptyDestination;

        window.location.reload();

        return;
      }

      if (!localDestination.startDate) {
        alert('Please add a START DATE for the destination');
        this.clickedDestination = this.emptyDestination;

        window.location.reload();

        return;
      }

      if (!localDestination.endDate) {
        alert('Please add an END DATE for the destination');
        this.clickedDestination = this.emptyDestination;

        window.location.reload();

        return;
      }

      this.showLoader = true;
      axios.put(DestinationService.getUrl() + '/' + localDestination.id, localDestination, {headers: authHeader()})
          .then((res) => {
            window.location.reload();
          })
          .catch((error) => {
            alert(JSON.stringify(error.response.data));
            console.log(JSON.stringify(error.response.data));
            window.location.reload();
          }).finally(() => {
        this.clickedDestination = this.emptyDestination;
        this.showLoader = false;
      });
    },

    onSubmitShowFilteredDestinations(e) {
      e.preventDefault()
      if (this.filterFormDestination.title.length === 0) {
        this.loadPageDestinations();
      } else {
        this.showLoader = true
        axios.get(DestinationService.getUrl() + '/destinations-with-given-title/' + this.filterFormDestination.title)
            .then((response) => {
              this.destinations = response.data;
            }).finally(() => {
          this.showLoader = false;
        });
      }
    },

    loadPageDestinations() {
      // if (!this.userAuthenticated) {
      //     this.divHeight = 380;
      //     this.showModalLogin = true;
      //
      //     return;
      // }

      this.showLoader = true
      const updatedPage = this.page - 1;
      axios.get((this.userAuthenticated ? DestinationService.getUrl() : DestinationService.getPublicUrl()) + '?page=' + updatedPage + '&size=' + this.recordsPerPage, {headers: authHeader()})
          .then((response) => {
            this.showLoader = false
            this.destinations = response.data.destinations;
            this.totalRecords = response.data.totalItems;
            this.totalPages = response.data.totalPages;
            this.page = response.data.currentPage + 1;
          }).catch((error) => {
        if (error.response) {
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);

          if (error.response.status === 401) {
            this.logOut();
          }
        }
      }).finally(() => {
        this.showLoader = false;
      });
    },

    onPageChangeDestinations(page) {
      this.page = page
      this.loadPageDestinations()
    },

    onChangeRecordsPerPage() {
      if (this.clickedTab === 'Destinations') {
        this.loadPageDestinations();
      }
    },

    gotoPageDestinations() {
      if (!isNaN(parseInt(this.enterPageNo))) {
        this.page = parseInt(this.enterPageNo)
        this.loadPageDestinations()
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

  },

  created() {
    this.clickedTab = 'Destinations';

    // if (this.userAuthenticated) {
      this.loadPageDestinations();
    // }

    this.escapeHandler = (e) => {
      if (e.key === 'Escape' && this.showModalLogin) {
        this.showModalLogin = false;
      }
      if (e.key === 'Escape' && this.showModalRegister) {
        this.showModalRegister = false;
      }
      if (e.key === 'Escape' && this.showModalUserDetails) {
        this.showModalUserDetails = false;
      }
      if (e.key === 'Escape' && this.showModalDestinations) {
        this.updateAndCloseModalDestinations();
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

.tabs {
  background-color: rgba(140, 197, 197, 0.82);
}

#destinationsTable {
  width: 100%;
  table-layout: auto;
}

#container {
  position: relative;
  width: 1000px;
  background-color: #ffffff;
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

.destination {
  background-color: #ac003e;
}
</style>