import { BrowserRouter, Routes, Route } from "react-router-dom";

import Index from "./pages/Index";
import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignUpPage";
import BoardList from "./pages/Board/BoardList";
import BoardCreate from "./pages/Board/BoardCreate";
import BoardDetail from "./pages/Board/BoardDetail";
import BoardMy from "./pages/Board/BoardMy";
import ChangeInfoPage from "./pages/ChangeInfoPage";
import NoMatch from "./routes/NoMatch";
import FindIdInput from "./components/FindId/FindIdInput";
import AdminUser from "./pages/admin/AdminUser";
import AdminBoard from "./pages/admin/AdminBoard";
import PrivateRouteAdmin from './components/PrivateRouteAdmin'
import FindPwInput from "./components/FindPassword/FindPwInput";
import FindPwOutput from "./components/FindPassword/FindPwOutput";
import PrivateRoute from "./components/PrivateRoute";
import { useSelector } from "react-redux";

export default function App() {
  const isLoggedIn = useSelector(state => state.isLoggedIn)
  return (
    <main>
      <BrowserRouter>
        {/* <NewNav /> */}
        <Routes>
          <Route path="/" element={<Index />} />
          <Route path="/klogin" element={<Index />} />
          <Route path="/klogout" element={<Index />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/board" element={<BoardList />} />
          <Route path="/board/create" element={<PrivateRoute component={BoardCreate} />} />
          <Route path="/board/detail/:no" element={<BoardDetail />} />
          <Route path="/board/my" element={<BoardMy />} />
          <Route path="/changeinfo" element={<ChangeInfoPage />} />
          <Route path="/findid" element={<FindIdInput />} />
          <Route path="/findpw" element={<FindPwInput />} />
          <Route path="/findpw/result" element={<FindPwOutput />} />
          <Route path="/Adminuser" element={<PrivateRouteAdmin component={AdminUser} />} />
          <Route path="/AdminBoard" element={<AdminBoard />} />
          <Route path="*" element={<NoMatch />} />
        </Routes>
        {/* <Footer /> */}
      </BrowserRouter>
    </main>
  );
}