job('test/Hello World') {
  description('Hello_world Job')
  steps {
    shell('echo "Hello World!"')
  }
}
