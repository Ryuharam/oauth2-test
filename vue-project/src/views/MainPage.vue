<template>
    <div>
      <h1>Welcome to the Main Page</h1>
      <p v-if="accessToken">Access Token: {{ accessToken }}</p>
      <p v-if="refreshToken">Refresh Token: {{ refreshToken }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  // 토큰 상태 관리
  const accessToken = ref('');
  const refreshToken = ref('');
  
  async function fetchTokens() {
    try {
      const response = await fetch('http://localhost:8080/redirect-path', {
        method: 'GET',
        credentials: 'include', // 쿠키 포함
      });
  
      const accessHeader = response.headers.get('Authorization');
      const refreshHeader = response.headers.get('Refresh-Token');
  
      if (accessHeader) {
        accessToken.value = accessHeader.replace('Bearer ', '');
        localStorage.setItem('accessToken', accessToken.value);
      }
      if (refreshHeader) {
        refreshToken.value = refreshHeader.replace('Bearer ', '');
        localStorage.setItem('refreshToken', refreshToken.value);
      }
    } catch (error) {
      console.error('Error fetching tokens:', error);
    }
  }
  
  // 리다이렉트된 후 실행
  onMounted(() => {
    fetchTokens();
  });
  </script>
  