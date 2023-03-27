export const login = (user) => ({
    type: 'LOGIN',
    payload: user
});

export const logout = (user) => ({
    type: 'LOGOUT',
    payload: user
});

export const setCurrentUser = decoded => ({
    type: 'SET_CURRENT_USER',
    payload: decoded
});

export const setMessage = (message) => ({
    type: 'SET_MESSAGE',
    payload: message
});

export const setLoading = (isLoading) => ({
    type: "SET_LOADING",
    payload: isLoading
});

export const setUserRole = (userRole) => ({
    type: "SET_USERROLE",
    payload: userRole
})

export const setNickName = (nickName) => ({
    type: "SET_NICKNAME",
    payload: nickName
})

export const setAuthentication = (authentication) => ({
    type: "SET_AUTHENTICATION",
    payload: authentication
})