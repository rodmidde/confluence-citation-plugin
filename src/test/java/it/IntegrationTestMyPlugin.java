package it;

public class IntegrationTestMyPlugin extends AbstractIntegrationTestCase
{
	public void testSomething()
	{
        gotoPage("display/ds/Home");
        assertTitleEquals("Home - Demonstration Space - Confluence");
	}
}
