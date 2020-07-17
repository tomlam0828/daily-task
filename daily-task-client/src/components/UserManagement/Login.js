import React, { Component } from 'react';
import { connect } from "react-redux";
import PropTypes from "prop-types";
import classnames from "classnames";
import { login } from "../../actions/securityActions";

class Login extends Component {

    constructor() {
        super();

        this.state = {
            username: "",
            password: ""
        }
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    onSubmit(e) {
        e.preventDefault();
        const loginRequest = {
            username: this.state.username,
            password: this.state.password
        }

        this.props.login(loginRequest);
    }

    render() {
        const { errors } = this.state
        return (
            <div className="login">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Log In</h1>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input
                                        type="text"
                                        className="form-control form-control-lg"
                                        placeholder="Email Address"
                                        name="username"
                                        value={this.state.username}
                                        onChange={this.onChange}
                                    />
                                    {
                                        // errors.username && (
                                        //     <div className="invalid-feedback">{errors.username}</div>
                                        // )
                                    }
                                </div>
                                <div className="form-group">
                                    <input
                                        type="password"
                                        className="form-control form-control-lg"
                                        placeholder="Password"
                                        name="password"
                                        value={this.state.password}
                                        onChange={this.onChange}
                                    />
                                    {
                                        // errors.username && (
                                        //     <div className="invalid-feedback">{errors.password}</div>
                                        // )
                                    }
                                </div>
                                <input
                                    type="submit"
                                    className="btn btn-info btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

Login.propTypes = {
    login: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.secutiry,
    errors: state.errors
})

export default connect(mapStateToProps, { login })(Login);


// className={classnames("form-control form-control-lg", {
//     "is-invalid": errors.password
// })}

// className={classnames("form-control form-control-lg", {
//     "is-invalid": errors.username
// })}