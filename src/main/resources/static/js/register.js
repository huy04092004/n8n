// document.getElementById("registerForm").addEventListener("submit", function(event) {
//     event.preventDefault();
//
//     const fullName = document.getElementById("fullName").value;
//     const email = document.getElementById("email").value;
//     const password = document.getElementById("password").value;
//
//     fetch("/auth/register", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify({ fullName, email, password })
//     })
//         .then(response => response.text())
//         .then(data => {
//             alert(data);
//             if (data === "Đăng ký thành công!") {
//                 window.location.href = "/login";
//             }
//         })
//         .catch(error => console.error("Lỗi:", error));
// });
