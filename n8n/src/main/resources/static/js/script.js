// const API_URL = "http://localhost:8080/auth"; // Thay đổi nếu backend chạy trên port khác
//
// // Hàm đăng nhập
// function login() {
//     const email = document.getElementById("loginEmail").value;
//     const password = document.getElementById("loginPassword").value;
//
//     fetch(`${API_URL}/login?email=${email}&password=${password}`, {
//         method: "POST"
//     })
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error("Sai email hoặc mật khẩu!");
//             }
//             return response.json();
//         })
//         .then(data => {
//             localStorage.setItem("user", JSON.stringify(data)); // Lưu thông tin user vào localStorage
//             document.getElementById("loginMessage").textContent = "Đăng nhập thành công!";
//             document.getElementById("loginMessage").style.color = "green";
//         })
//         .catch(error => {
//             document.getElementById("loginMessage").textContent = error.message;
//         });
// }
//
// // Hàm đăng ký
// function register() {
//     const fullName = document.getElementById("fullName").value;
//     const email = document.getElementById("registerEmail").value;
//     const phone = document.getElementById("phone").value;
//     const password = document.getElementById("registerPassword").value;
//
//     const userData = {
//         fullName: fullName,
//         email: email,
//         phone: phone,
//         password: password,
//         role: "USER"
//     };
//
//     fetch(`${API_URL}/register`, {
//         method: "POST",
//         headers: { "Content-Type": "application/json" },
//         body: JSON.stringify(userData)
//     })
//         .then(response => response.text())
//         .then(message => {
//             document.getElementById("registerMessage").textContent = message;
//             document.getElementById("registerMessage").style.color = message.includes("thành công") ? "green" : "red";
//         })
//         .catch(error => {
//             document.getElementById("registerMessage").textContent = "Lỗi khi đăng ký!";
//         });
// }
