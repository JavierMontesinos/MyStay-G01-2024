describe('Login screen', () => {
  beforeAll(async () => {
    await device.launchApp();
  });

  beforeEach(async () => {
    await device.reloadReactNative();
  });

  it('Login form should be visible', async () => {
    await expect(element(by.id('dni-login'))).toBeVisible();
    await expect(element(by.id('nhab-login'))).toBeVisible();
    await expect(element(by.id('btn-login'))).toBeVisible();
  });

  
  it('Should login with provided credentials', async () => {
    await element(by.id('dni-login')).typeText('12345');
    await element(by.id('nhab-login')).typeText('101\n');
    
    await element(by.id('btn-login')).tap();
  });

});
