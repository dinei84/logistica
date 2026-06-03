package com.logistica.auth;


public record RegisterDTO(String username, String password, String nome, Role role) {}
