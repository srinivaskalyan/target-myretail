package com.target.redsky.myretail.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.target.redsky.myretail.exception.handler.FailedToUpdateProductException;
import com.target.redsky.myretail.exception.handler.ProductNotFoundException;
import com.target.redsky.myretail.exception.handler.ProductPriceNotFoundInDBException;
import com.target.redsky.myretail.model.CurrentPrice;
import com.target.redsky.myretail.model.ProductInformation;
import com.target.redsky.myretail.model.ProductPrice;
import com.target.redsky.myretail.repository.ProductInformationRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductInformationServiceImplTest {

	@InjectMocks
	private ProductInformationServiceImpl mockProductInformationService;

	@Mock
	private ProductInformationRepository mockProductInfoRepo;

	@Mock
	private Environment configs;

	@Mock
	private RestTemplate restTemplate;
	
	private ProductPrice dummyProductPrice;
	
	private int dummyProductId;

	private String dummyProductInformation = "{\n" + "  \"product\": {\n" + "    \"available_to_promise_network\": {\n"
			+ "      \"is_loyalty_purchase_enabled\": false,\n" + "      \"available_to_promise_quantity\": 90,\n"
			+ "      \"multichannel_options\": [],\n" + "      \"is_infinite_inventory\": false,\n"
			+ "      \"availability\": \"AVAILABLE\",\n" + "      \"stores_available_to_promise_quantity\": 0,\n"
			+ "      \"street_date\": \"2011-11-15T06:00:00.000Z\",\n"
			+ "      \"availability_status\": \"IN_STOCK\",\n"
			+ "      \"is_out_of_stock_in_all_store_locations\": false,\n"
			+ "      \"is_out_of_stock_in_all_online_locations\": false,\n" + "      \"product_id\": \"13860428\",\n"
			+ "      \"loyalty_purchase_start_date_time\": \"1970-01-01T00:00:00.000Z\",\n"
			+ "      \"id_type\": \"TCIN\",\n" + "      \"loyalty_availability_status\": \"IN_STOCK\",\n"
			+ "      \"online_available_to_promise_quantity\": 90\n" + "    },\n" + "    \"item\": {\n"
			+ "      \"return_method\": \"This item can be returned to any Target store or Target.com.\",\n"
			+ "      \"gifting_enabled\": false,\n" + "      \"item_state\": \"READY_FOR_LAUNCH\",\n"
			+ "      \"specifications\": [],\n"
			+ "      \"ship_to_restriction\": \"This item cannot be shipped to the following locations: United States Minor Outlying Islands, American Samoa, Puerto Rico, Northern Mariana Islands, Virgin Islands, U.S., APO/FPO, Guam\",\n"
			+ "      \"manufacturer\": {},\n" + "      \"subscription_eligible\": false,\n"
			+ "      \"recall_compliance\": {\n" + "        \"is_product_recalled\": false\n" + "      },\n"
			+ "      \"package_dimensions\": {\n" + "        \"dimension_unit_of_measure\": \"INCH\",\n"
			+ "        \"depth\": \"6.65\",\n" + "        \"width\": \"5.33\",\n" + "        \"weight\": \"0.18\",\n"
			+ "        \"weight_unit_of_measure\": \"POUND\",\n" + "        \"height\": \"0.46\"\n" + "      },\n"
			+ "      \"enrichment\": {\n" + "        \"images\": [\n" + "          {\n"
			+ "            \"content_labels\": [\n" + "              {\n"
			+ "                \"image_url\": \"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\"\n" + "              }\n"
			+ "            ],\n" + "            \"base_url\": \"https://target.scene7.com/is/image/Target/\",\n"
			+ "            \"primary\": \"GUEST_44aeda52-8c28-4090-85f1-aef7307ee20e\"\n" + "          }\n"
			+ "        ],\n" + "        \"sales_classification_nodes\": [\n" + "          {\n"
			+ "            \"node_id\": \"hp0vg\"\n" + "          },\n" + "          {\n"
			+ "            \"node_id\": \"5xswx\"\n" + "          }\n" + "        ]\n" + "      },\n"
			+ "      \"estore_item_status_code\": \"A\",\n" + "      \"return_policies\": {\n"
			+ "        \"user\": \"Regular Guest\",\n" + "        \"policyDays\": \"30\",\n"
			+ "        \"guestMessage\": \"This item must be returned within 30 days of the ship date. See return policy for details.\"\n"
			+ "      },\n" + "      \"bundle_components\": {},\n" + "      \"product_brand\": {\n"
			+ "        \"facet_id\": \"55zki\",\n" + "        \"brand\": \"Universal Home Video\",\n"
			+ "        \"manufacturer_brand\": \"Universal Home Video\"\n" + "      },\n" + "      \"ribbons\": [],\n"
			+ "      \"product_description\": {\n"
			+ "        \"downstream_description\": \"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\",\n"
			+ "        \"bullet_description\": [\n" + "          \"<B>Movie Studio:</B> Universal Studios\",\n"
			+ "          \"<B>Movie Genre:</B> Comedy\",\n" + "          \"<B>Run Time (minutes):</B> 119\",\n"
			+ "          \"<B>Software Format:</B> Blu-ray\"\n" + "        ],\n"
			+ "        \"title\": \"The Big Lebowski (Blu-ray)\"\n" + "      },\n" + "      \"display_option\": {\n"
			+ "        \"is_size_chart\": false\n" + "      },\n" + "      \"dpci\": \"058-34-0436\",\n"
			+ "      \"tcin\": \"13860428\",\n" + "      \"country_of_origin\": \"US\",\n"
			+ "      \"is_proposition_65\": false,\n" + "      \"upc\": \"025192110306\",\n"
			+ "      \"buy_url\": \"https://www.target.com/p/the-big-lebowski-blu-ray/-/A-13860428\",\n"
			+ "      \"tax_category\": {\n" + "        \"tax_code_id\": 99999,\n" + "        \"tax_code\": \"99999\",\n"
			+ "        \"tax_class\": \"G\"\n" + "      },\n" + "      \"packaging\": {\n"
			+ "        \"is_retail_ticketed\": false\n" + "      },\n" + "      \"tags\": [],\n"
			+ "      \"product_vendors\": [\n" + "        {\n" + "          \"manufacturer_style\": \"025192110306\",\n"
			+ "          \"vendor_name\": \"Ingram Entertainment\",\n" + "          \"id\": \"1984811\"\n"
			+ "        },\n" + "        {\n" + "          \"manufacturer_style\": \"61119422\",\n"
			+ "          \"vendor_name\": \"UNIVERSAL HOME VIDEO\",\n" + "          \"id\": \"4667999\"\n"
			+ "        },\n" + "        {\n" + "          \"manufacturer_style\": \"61119422\",\n"
			+ "          \"vendor_name\": \"Universal Home Ent PFS\",\n" + "          \"id\": \"1979650\"\n"
			+ "        }\n" + "      ],\n" + "      \"product_classification\": {\n"
			+ "        \"product_type\": \"542\",\n" + "        \"product_type_name\": \"ELECTRONICS\",\n"
			+ "        \"item_type_name\": \"Movies\",\n" + "        \"item_type\": {\n"
			+ "          \"name\": \"movies\",\n" + "          \"category_type\": \"Item Type: MMBV\",\n"
			+ "          \"type\": 300752\n" + "        }\n" + "      },\n"
			+ "      \"environmental_segmentation\": {\n" + "        \"is_hazardous_material\": false,\n"
			+ "        \"has_lead_disclosure\": false\n" + "      },\n"
			+ "      \"relationship_type_code\": \"Stand Alone\",\n" + "      \"handling\": {},\n"
			+ "      \"attributes\": {\n" + "        \"merch_subclass\": 34,\n"
			+ "        \"return_method\": \"This item can be returned to any Target store or Target.com.\",\n"
			+ "        \"gift_wrapable\": \"N\",\n" + "        \"has_prop65\": \"N\",\n"
			+ "        \"media_format\": \"Blu-ray\",\n" + "        \"is_hazmat\": \"N\",\n"
			+ "        \"max_order_qty\": 10,\n" + "        \"manufacturing_brand\": \"Universal Home Video\",\n"
			+ "        \"merch_class\": \"MOVIES\",\n"
			+ "        \"ship_to_restriction\": \"United States Minor Outlying Islands,American Samoa (see also separate entry under AS),Puerto Rico (see also separate entry under PR),Northern Mariana Islands,Virgin Islands, U.S.,APO/FPO,Guam (see also separate entry under GU)\",\n"
			+ "        \"street_date\": \"2011-11-15\",\n" + "        \"merch_classid\": 58\n" + "      },\n"
			+ "      \"fulfillment\": {\n" + "        \"box_percent_filled_by_weight\": 0.43,\n"
			+ "        \"po_box_prohibited_message\": \"We regret that this item cannot be shipped to PO Boxes.\",\n"
			+ "        \"is_po_box_prohibited\": true,\n" + "        \"box_percent_filled_by_volume\": 0.27,\n"
			+ "        \"box_percent_filled_display\": 0.43\n" + "      }\n" + "    },\n" + "    \"circle_offers\": {\n"
			+ "      \"universal_offer_exists\": false,\n" + "      \"non_universal_offer_exists\": true\n" + "    }\n"
			+ "  }\n" + "}";

	@Before
	public void setUp() {
		dummyProductId = 13860428;
		
		dummyProductPrice = new ProductPrice();
		dummyProductPrice.set_id(new BigInteger("2432902008176640000"));
		dummyProductPrice.setCurrency("USD");
		dummyProductPrice.setPrice(59.00);
		dummyProductPrice.setProductId(dummyProductId);
	}
	
	@Test
	public void getProductInformationForGivenProductIdTest() {
		String baseUrlPrefix = "http://www.dummy.testprefix/";
		String baseUrlSuffix = "/testsuffix";
		when(mockProductInfoRepo.findByProductId(dummyProductId)).thenReturn(dummyProductPrice);
		when(configs.getProperty("baseUrlPrefix")).thenReturn(baseUrlPrefix);
		when(configs.getProperty("baseUrlSuffix")).thenReturn(baseUrlSuffix);
		when(restTemplate.getForObject(eq(baseUrlPrefix + dummyProductId + baseUrlSuffix), eq(String.class)))
				.thenReturn(dummyProductInformation);
		ProductInformation resultProductInformation = mockProductInformationService.getProductInformation(dummyProductId);
		assertEquals("USD", resultProductInformation.getCurrent_price().getCurrency_code());
	}

	/*
	 * Test to validate the updation of productId = 13860428 currency from USD to
	 * AUD.
	 */
	@Test
	public void updateProductPriceRecordInDBTest() {
		
		ProductInformation productToUpdate = new ProductInformation();
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setCurrency_code("AUD");
		currentPrice.setValue(60.0);
		productToUpdate.setCurrent_price(currentPrice);
		productToUpdate.setId(dummyProductId);
		productToUpdate.setName("Market Pantry");

		when(mockProductInfoRepo.findByProductId(dummyProductId)).thenReturn(dummyProductPrice);
		ProductPrice resultProductPrice = mockProductInformationService.updateProductInformation(dummyProductId,
				productToUpdate);
		assertEquals("AUD", resultProductPrice.getCurrency());
		assertEquals(dummyProductId, resultProductPrice.getProductId());
	}
	

	@Test(expected = ProductNotFoundException.class)
	public void productNameNotFoundForGivenProductIdTest() {
		String baseUrlPrefix = "http://www.dummy.testprefix/";
		String baseUrlSuffix = "/testsuffix";
		when(configs.getProperty("baseUrlPrefix")).thenReturn(baseUrlPrefix);
		when(configs.getProperty("baseUrlSuffix")).thenReturn(baseUrlSuffix);
		when(restTemplate.getForObject(eq(baseUrlPrefix + dummyProductId + baseUrlSuffix), eq(String.class)))
				.thenThrow(ProductNotFoundException.class);
		mockProductInformationService.getProductInformation(dummyProductId);
	}

	@Test(expected = ProductPriceNotFoundInDBException.class)
	public void productPriceNotFoundInDBForGivenProductIdTest() {
		String baseUrlPrefix = "http://www.dummy.testprefix/";
		String baseUrlSuffix = "/testsuffix";
		when(configs.getProperty("baseUrlPrefix")).thenReturn(baseUrlPrefix);
		when(configs.getProperty("baseUrlSuffix")).thenReturn(baseUrlSuffix);
		when(restTemplate.getForObject(eq(baseUrlPrefix + dummyProductId + baseUrlSuffix), eq(String.class)))
				.thenReturn(dummyProductInformation);
		when(mockProductInfoRepo.findByProductId(dummyProductId)).thenReturn(null).thenThrow(ProductPriceNotFoundInDBException.class);
		mockProductInformationService.getProductInformation(dummyProductId);
	}

	@Test(expected = FailedToUpdateProductException.class)
	public void failedToUpdateProductInformationForGivenProductIdTest() {
		
		ProductInformation productToUpdate = new ProductInformation();
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setCurrency_code("AUD");
		currentPrice.setValue(60.00);
		productToUpdate.setCurrent_price(currentPrice);
		productToUpdate.setId(dummyProductId);
		productToUpdate.setName("Market Pantry");

		when(mockProductInfoRepo.findByProductId(dummyProductId)).thenReturn(null).thenThrow(FailedToUpdateProductException.class)
				.thenThrow(FailedToUpdateProductException.class);
		mockProductInformationService.updateProductInformation(dummyProductId, productToUpdate);
	}
}
