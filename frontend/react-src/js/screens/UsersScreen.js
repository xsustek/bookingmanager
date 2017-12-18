import React from "react";
import UserStore from "../stores/User/UserStore";
import { removeUserItem, resgisterUserItem } from "../stores/User/UserAction";
import UserList from "../components/UserList";

export default class UsersScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            items: null,
            errors: null,
            formName: '',
            formAddress: '',
            formPhone: '',
            formEmail: '',
            formPassword: '',
            formRole: 'USER'
        };

        this.validateForm = this.validateForm.bind(this);
        this.handleCreate = this.handleCreate.bind(this);
        this.loadItems = this.loadItems.bind(this);

        // init
        UserStore.addChangeListener(this.loadItems);
        this.loadItems();

    }

    loadItems() {
        UserStore.getAllItems().then(items => {
            this.setState({
                items: items
            })
        });
    }

    componentWillUnmount() {
        UserStore.removeChangeListener(this.loadItems);
    }

    handleCreate(e) {
        e.preventDefault();

        if (!this.validateForm()) {
            return;
        }

        resgisterUserItem({
            'fullName': this.state.formName,
            'address': this.state.formAddress,
            'email': this.state.formEmail,
            'phoneNumber': this.state.formPhone,
            'password': this.state.formPassword,
            'role': this.state.formRole,
        });

        this.setState({
            formName: '',
            formAddress: '',
            formPhone: '',
            formEmail: '',
            formPassword: '',
            formRole: '',
            errors: null
        })
    }

    validateForm() {
        const { formName, formAddress, formEmail, formPhone, formPassword, formRole } = this.state;

        if (formName.trim().length <= 3) {
            this.setState({
                errors: {
                    name: 'The name must be at least 3 characters.'
                }
            })
            return false;
        }

        if (formAddress.trim().length <= 3) {
            this.setState({
                errors: {
                    address: 'The address must be at least 3 characters.'
                }
            })
            return false;
        }

        if (formEmail.trim().length <= 3) {
            this.setState({
                errors: {
                    email: 'The email must be at least 3 characters.'
                }
            })
            return false;
        }

        if (formPassword.trim().length <= 3) {
            this.setState({
                errors: {
                    password: 'The password must be at least 3 characters.'
                }
            })
            return false;
        }

        if (formPhone.trim().length <= 3) {
            this.setState({
                errors: {
                    phone: 'The phone must be at least 3 characters.'
                }
            })
            return false;
        }

        if (formRole != 'USER' && formRole != 'ADMIN') {
            this.setState({
                errors: {
                    role: 'The role must be one of these values USER or ADMIN.'
                }
            })
            return false;
        }

        return true;
    }

    handleOnRemove(id) {
        removeUserItem({
            id: id
        });
    }

    render() {

        const { items, errors } = this.state;
        return items && (
            <div>
                <h2>Users</h2>

                <h4>Create new one</h4>
                <div className="row">
                    <div className="col-md-12">
                        {errors && (
                            <div className="alert alert-dismissible alert-danger">
                                {errors.name && <p>{errors.name}</p>}
                                {errors.address && <p>{errors.address}</p>}
                                {errors.phone && <p>{errors.phone}</p>}
                                {errors.email && <p>{errors.email}</p>}
                                {errors.password && <p>{errors.password}</p>}
                                {errors.role && <p>{errors.role}</p>}
                            </div>
                        )}
                        <form className="form-horizontal" onSubmit={this.handleCreate}>
                            <div className="col-md-6">
                                <div className={"form-group" + (errors && errors.name ? ' has-error' : '')}>
                                    <label htmlFor="inputName" className="col-lg-2 control-label">Name</label>
                                    <div className="col-lg-10">
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="inputName"
                                            placeholder="Name"
                                            onChange={e => this.setState({ formName: e.target.value })}
                                            value={this.state.formName}
                                            name="fullName" />
                                    </div>
                                </div>

                                <div className={"form-group" + (errors && errors.address ? ' has-error' : '')}>
                                    <label htmlFor="inputAddress" className="col-lg-2 control-label">Address</label>
                                    <div className="col-lg-10">
                                        <input name="address"
                                            onChange={e => this.setState({ formAddress: e.target.value })}
                                            value={this.state.formAddress}
                                            type="text"
                                            className="form-control"
                                            id="inputAddress"
                                            placeholder="Address" />
                                    </div>
                                </div>

                                <div className={"form-group" + (errors && errors.phone ? ' has-error' : '')}>
                                    <label htmlFor="inputPhone" className="col-lg-2 control-label">Phone Number</label>
                                    <div className="col-lg-10">
                                        <input name="phoneNumber"
                                            onChange={e => this.setState({ formPhone: e.target.value })}
                                            value={this.state.formPhone}
                                            type="text"
                                            className="form-control"
                                            id="inputPhone"
                                            placeholder="Phone Number" />
                                    </div>
                                </div>




                            </div>

                            <div className="col-md-6">

                                <div className={"form-group" + (errors && errors.email ? ' has-error' : '')}>
                                    <label htmlFor="inputEmail" className="col-lg-2 control-label">Email</label>
                                    <div className="col-lg-10">
                                        <input name="email"
                                            onChange={e => this.setState({ formEmail: e.target.value })}
                                            value={this.state.formEmail}
                                            type="email"
                                            className="form-control"
                                            id="inputEmail"
                                            placeholder="Email" />
                                    </div>
                                </div>
                                <div className={"form-group" + (errors && errors.password ? ' has-error' : '')}>
                                    <label htmlFor="inputPassword" className="col-lg-2 control-label">Password</label>
                                    <div className="col-lg-10">
                                        <input name="password"
                                            onChange={e => this.setState({ formPassword: e.target.value })}
                                            value={this.state.formPassword}
                                            type="password"
                                            className="form-control"
                                            id="inputPassword"
                                            placeholder="Password" />
                                    </div>
                                </div>
                            </div>

                            <div className="col-md-6">
                                <div className={"form-group" + (errors && errors.role ? ' has-error' : '')}>
                                    <label htmlFor="inputRole" className="col-lg-2 control-label">Role</label>
                                    <div className="col-lg-10">
                                        <select
                                            onChange={e => this.setState({ formRole: e.target.value })}
                                            value={this.state.formRole}
                                            type="text"
                                            className="form-control"
                                            id="inputRole"
                                            placeholder="Role"
                                            className="form-control"
                                        >
                                            <option value="USER">USER</option>
                                            <option value="ADMIN">ADMIN</option>
                                        </select>
                                        {/* <input name="role"
                                            onChange={e => this.setState({ formRole: e.target.value })}
                                            value={this.state.formRole}
                                            type="text"
                                            className="form-control"
                                            id="inputRole"
                                            placeholder="Role" /> */}
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-12">
                                <div className="form-group">
                                    <div className="col-lg-10 col-lg-offset-2">
                                        <button type="submit" className="btn btn-success">Create</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <hr />


                <UserList items={items} onRemove={this.handleOnRemove} />
            </div>
        );
    }
}