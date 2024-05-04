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

  
  it('Login form should be visible', async () => {
    await expect(element(by.id('dni-login'))).toBeVisible();
    await expect(element(by.id('nhab-login'))).toBeVisible();
    await expect(element(by.id('btn-login'))).toBeVisible();
  });

});
