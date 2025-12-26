let navigate;

export const setNavigate = (nav) => {
  navigate = nav;
};

export const redirectToLogin = () => {
  if (navigate) {
    navigate("/login", { replace: true });
  }
};
