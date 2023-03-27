import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate, Route } from 'react-router-dom';

const PrivateRoute = ({ component: Component, ...rest }) => {
    const isLoggedIn = useSelector(state => state.isLoggedIn);
    const userRole = useSelector(state => state.userRole);
    console.log(isLoggedIn)
    console.log(userRole)

    if (isLoggedIn && (userRole === 'ROLE_USER' || userRole === 'ROLE_ADMIN')) {
        return <Component />
    } else {
        return <>{alert("로그인이 필요합니다.")}<Navigate to="/login" replace /></>
    }
};

export default PrivateRoute;
